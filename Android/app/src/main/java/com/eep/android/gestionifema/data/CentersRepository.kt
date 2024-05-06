package com.eep.android.gestionifema.data

import com.eep.android.gestionifema.model.Center

object CentersRepository {
    val centers = listOf(
        Center(
            id = 1,
//            name = "Universidad Nacional",
//            imageUrl = "https://example.com/universidad1.jpg",
//            webUrl = "https://www.universidadnacional.edu",
//            description = "La Universidad Nacional es reconocida por su excelencia académica en ciencias y humanidades.",
            type = "Universidad"
        ),
        Center(
            id = 2,
//            name = "Centro de Formación Profesional Superior Tech",
//            imageUrl = "https://example.com/centrofp1.jpg",
//            webUrl = "https://www.centrofptech.edu",
//            description = "El Centro FP Superior Tech ofrece formación de vanguardia en tecnologías emergentes y aplicadas.",
            type = "FormacionProfesional"
        ),
        // Añade más centros según sea necesario
    )

    private fun Center(id: Int, type: String): Center {
        return centers.find { it.id == id } ?: error("Center not found")

    }

    fun getCenterById(id: Int): Center? = centers.find { it.id == id }
}