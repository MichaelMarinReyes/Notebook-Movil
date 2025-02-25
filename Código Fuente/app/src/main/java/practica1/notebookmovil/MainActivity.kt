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

        val resultadoTexto = parser.getTexto()

        val textResult = TextView(context).apply {
            text = "✅ $resultadoTexto"
            setTextColor(context.getColor(android.R.color.holo_green_dark))
            textSize = obtenerTamañoTexto(textoIngresado)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }

        nuevaVista.addView(textExpression)
        nuevaVista.addView(textResult)

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