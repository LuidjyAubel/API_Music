package fr.aubel.music.models

import javax.persistence.*


@Entity
data class Music(
    @Id
    var Id : String,
    var Name : String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "band_id", referencedColumnName = "Id")
    var Band : Band,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "genre_id", referencedColumnName = "Id")
    var Genre : Genre,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "album_id", referencedColumnName = "Id")
    var album : Album
)
