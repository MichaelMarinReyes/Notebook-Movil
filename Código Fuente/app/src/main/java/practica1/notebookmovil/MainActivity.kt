package practica1.notebookmovil

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import practica1.notebookmovil.analizadores.Lexer
import practica1.notebookmovil.analizadores.Parser
import practica1.notebookmovil.reportes.OcurrenciaOperacion
import practica1.notebookmovil.ui.theme.NotebookMóvilTheme
import java.io.StringReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.vista_principal)

        val entradaTexto = findViewById<EditText>(R.id.entradaTextoTextEdit)
        val compilarBtn = findViewById<Button>(R.id.compilarBoton)
        val contenedorResultados = findViewById<ViewGroup>(R.id.contenedorResultados)
        val scrollView = findViewById<ScrollView>(R.id.scrollView)

        compilarBtn.setOnClickListener {
            val textoIngresado = entradaTexto.text.toString().trim()
            if (textoIngresado.isNotEmpty()) {
                agregarExpresion(this, textoIngresado, contenedorResultados, scrollView)
                entradaTexto.text.clear()
            }
            if (textoIngresado.isEmpty()) {
                Toast.makeText(this, "Ingresa un texto en el campo indicado", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

private fun agregarExpresion(context: Context, textoIngresado: String, contenedorResultados: ViewGroup, scrollView: ScrollView) {
    val nuevaVista = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        setPadding(8, 8, 8, 8)
    }

    val textExpression = TextView(context).apply {
        text = "🔹 $textoIngresado"
        textSize = 18f
    }

    try {
        val lexer = Lexer(StringReader(textoIngresado))
        val parser = Parser(lexer)
        parser.parse()
        Log.d("DEBUG", "Fin de parseo")
        Log.d("INFO", textoIngresado)
        val resultadoTexto = parser.getTexto()
        Log.d("INFO", resultadoTexto)
        if (textoIngresado == "ocurrencia") {
            reporteOcurrencias(context, contenedorResultados, parser.reporteOcurrencia)
        } else {
            val textResult = TextView(context).apply {
                text = "✅ $resultadoTexto"
                setTextColor(context.getColor(android.R.color.holo_green_dark))
                textSize = obtenerTamañoTexto(textoIngresado)
                setTypeface(typeface, obtenerTipoTexto(textoIngresado))
            }
            nuevaVista.addView(textExpression)
            nuevaVista.addView(textResult)
        }

    } catch (e: Exception) {
        val textError = TextView(context).apply {
            text = "❌ Error: ${e.message}"
            textSize = 16f
            setTextColor(context.getColor(android.R.color.holo_red_dark))
        }

        nuevaVista.addView(textExpression)
        nuevaVista.addView(textError)
    }

    contenedorResultados.addView(nuevaVista)

    scrollView.post {
        scrollView.fullScroll(View.FOCUS_DOWN)
    }
}

private fun obtenerTamañoTexto(texto: String): Float {
    return when {
        texto.startsWith("#######") -> 19f
        texto.startsWith("#####") -> 20f
        texto.startsWith("####") -> 22f
        texto.startsWith("###") -> 24f
        texto.startsWith("##") -> 26f
        texto.startsWith("#") -> 28f
        else -> 16f
    }
}

private fun obtenerTipoTexto(texto: String): Int {
    return when {
        texto.startsWith("***") -> android.graphics.Typeface.BOLD_ITALIC
        texto.startsWith("**") -> android.graphics.Typeface.BOLD
        texto.startsWith("*") -> android.graphics.Typeface.ITALIC
        else -> android.graphics.Typeface.NORMAL
    }
}

private fun reporteOcurrencias(context: Context, contenedorResultados: ViewGroup, ocurrencias: ArrayList<OcurrenciaOperacion>) {
    val tableLayout = TableLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        setPadding(8, 8, 8, 8)
    }

    val headerRow = TableRow(context).apply {
        val headerNumero = TextView(context).apply { text = " " }
        val headerOperador = TextView(context).apply { text = "Operador" }
        val headerColumna = TextView(context).apply { text = "Columna" }
        val headerOcurrencia = TextView(context).apply { text = "Ocurrencia" }

        addView(headerNumero)
        addView(headerOperador)
        addView(headerColumna)
        addView(headerOcurrencia)
    }

    tableLayout.addView(headerRow)

    for (i in 1..5) {
        val tableRow = TableRow(context).apply {
            var operador = ocurrencias.get(i).tipoOperacion
            var columna = ocurrencias.get(i).columna
            var ocurrencia = ocurrencias.get(i).ocurrencia
            val numeroText = TextView(context).apply { text = i.toString() }
            val operadorText = TextView(context).apply { text = "$operador" }
            val columnaText = TextView(context).apply { text = "$columna" }
            val ocurrenciaText = TextView(context).apply { text = "$ocurrencia" }

            addView(numeroText)
            addView(operadorText)
            addView(columnaText)
            addView(ocurrenciaText)
        }
        tableLayout.addView(tableRow)
    }

    contenedorResultados.addView(tableLayout)
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotebookMóvilTheme {
        Greeting("Android")
    }
}