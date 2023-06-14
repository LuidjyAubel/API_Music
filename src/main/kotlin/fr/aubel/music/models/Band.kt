package fr.aubel.music.models

import javax.persistence.*

@Entity
data class Band(
    @Id
    var Id : String,
    var Name : String,
    @OneToMany(mappedBy="Id" )
    val Members : MutableList<Member> = mutableListOf()
)
