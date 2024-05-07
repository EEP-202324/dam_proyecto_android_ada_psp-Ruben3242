package com.eep.android.gestionifema.data

import com.eep.android.gestionifema.data.ListaCentros.listaCentros
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
}

object ListaCentros {
    val listaCentros = listOf(
        Center(
            id = 1,
            nombreCentro = "Universidad Nacional",
            paginaWeb = "https://www.universidadnacional.edu",
            type = "Universidad",
            descripcion = "La Universidad Nacional es reconocida por su excelencia académica en ciencias y humanidades."
        ),
        Center(
            id = 2,
            nombreCentro = "Centro de Formación Profesional Superior Tech",
            paginaWeb = "https://www.centrofptech.edu",
            type = "FormacionProfesional",
            descripcion = "El Centro FP Superior Tech ofrece formación de vanguardia en tecnologías emergentes y aplicadas."
        ),
        Center(
            id = 3,
            nombreCentro = "Centro de Formación Profesional de Diseño",
            paginaWeb = "https://www.centrofpdiseno.edu",
            type = "FormacionProfesional",
            descripcion = "El Centro FP de Diseño es un referente en la formación de profesionales creativos y talentosos."
        ),
        Center(
            id = 4,
            nombreCentro = "Instituto de Ciencias Aplicadas",
            paginaWeb = "https://www.institutociencias.edu",
            type = "Universidad",
            descripcion = "El Instituto de Ciencias Aplicadas es líder en investigación y desarrollo tecnológico en el país."
        ),
        Center(
            id = 5,
            nombreCentro = "Centro de Formación Profesional de Gastronomía",
            paginaWeb = "https://www.centrofpgastronomia.edu",
            type = "FormacionProfesional",
            descripcion = "El Centro FP de Gastronomía es reconocido por su excelencia en la formación de chefs y profesionales de la cocina."
        )
    )
}
fun findCenterById(centerId: Int): Center? {
    return listaCentros.find { it.id == centerId }
}