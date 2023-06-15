package fr.aubel.music.models

import javax.persistence.*

@Entity
data class Member(
    @Id
    var Id : String,
    var FirstName : String,
    var LastName : String,
    var NickName : String? = null
)
