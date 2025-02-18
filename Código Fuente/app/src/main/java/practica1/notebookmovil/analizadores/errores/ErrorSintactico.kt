package practica1.notebookmovil.analizadores.errores

import android.os.Parcel
import android.os.Parcelable

class ErrorSintactico(
    val tipo: String?,
    val linea: Int,
    val columna: Int,
    val lexema: String?,
    val descripcion: String?
)  : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tipo)
        parcel.writeInt(linea)
        parcel.writeInt(columna)
        parcel.writeString(lexema)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<practica1.notebookmovil.analizadores.errores.ErrorSintactico> {
        override fun createFromParcel(parcel: Parcel): practica1.notebookmovil.analizadores.errores.ErrorSintactico {
            return practica1.notebookmovil.analizadores.errores.ErrorSintactico(parcel)
        }

        override fun newArray(size: Int): Array<practica1.notebookmovil.analizadores.errores.ErrorSintactico?> {
            return arrayOfNulls(size)
        }
    }
}