package practica1.notebookmovil

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.vista_principal)

        val entradaTexto = findViewById<EditText>(R.id.entradaTextoTextEdit)
        val compilarBtn = findViewById<Button>(R.id.compilarBoton)
        val contenedorResultados = findViewById<ViewGroup>(R.id.contenedorResultados)

        compilarBtn.setOnClickListener {
            val textoIngresado = entradaTexto.text.toString().trim()
            if (textoIngresado.isNotEmpty()) {
                agregarExpresion(this, textoIngresado, contenedorResultados)
                entradaTexto.text.clear()
            }
            if (textoIngresado.isEmpty()) {
                Toast.makeText(this, "Ingresa un texto en el campo indicado", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

private fun agregarExpresion(context: Context, expresion: String, contenedorResultados: ViewGroup) {
    val nuevaVista = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        setPadding(8, 8, 8, 8)
    }

    val textExpression = TextView(context).apply {
        text = "🔹 $expresion"
        textSize = 18f
    }

    try {
        val lexer = Lexer(StringReader(expresion))
        Log.d("DEBUG", "Expresión recibida: $expresion")
        val parser = Parser(lexer)
        val resultado = parser.parse().value
        val texto = parser.getTexto()
        Log.d("DEBUG", "Expresión del parser: $resultado")
        val textResult = TextView(context).apply {
            text = "✅ Resultado: $texto"
            textSize = 16f
            setTextColor(context.getColor(android.R.color.holo_green_dark))
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