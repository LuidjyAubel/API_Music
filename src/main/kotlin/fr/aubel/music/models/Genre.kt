package fr.aubel.music.models

import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Genre(
    @Id
    var Id : String,
    var Name : String
)
