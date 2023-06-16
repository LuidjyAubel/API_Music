package fr.aubel.music.models

import javax.persistence.*

@Entity
data class Band(
    @Id
    var Id : String,
    var Name : String,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "band_member",
        joinColumns = [JoinColumn(name = "band_id")],
        inverseJoinColumns = [JoinColumn(name = "member_id")]
    )
    val members: MutableList<Member> = mutableListOf()
)
