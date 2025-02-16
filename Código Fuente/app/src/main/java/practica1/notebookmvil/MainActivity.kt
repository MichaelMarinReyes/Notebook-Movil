package practica1.notebookmvil

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import practica1.notebookmvil.ui.theme.NotebookMóvilTheme

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
                agregarExpresion(this, textoIngresado, contenedorResultados) // Pasar el contexto
            }
        }
    }
}


private fun agregarExpresion(context: Context, expresion: String, contenedorResultados: ViewGroup) {
    val nuevaVista = LinearLayout(context).apply { // Usar "context" en vez de "this"
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        setPadding(8, 8, 8, 8)
    }

    val textExpresion = TextView(context).apply {
        text = "📝 $expresion"
        textSize = 18f
    }

    val textError = TextView(context).apply {
        text = "❌ Error: '$expresion' no está definida"
        textSize = 16f
        setTextColor(context.getColor(android.R.color.holo_red_dark))
    }

    nuevaVista.addView(textExpresion)
    nuevaVista.addView(textError)

    contenedorResultados.addView(nuevaVista) // Agregar la vista generada
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