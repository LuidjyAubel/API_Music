package fr.aubel.music.models

import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Album(
    @Id
    var Id : String,
    var Name :String,
    var date : String
)
