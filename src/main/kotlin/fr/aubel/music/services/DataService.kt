package fr.aubel.music.services

import fr.aubel.music.dao.*
import fr.aubel.music.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DataService {
    @Autowired
    private lateinit var genreDao : GenreDao
    @Autowired
    private lateinit var memberDao: MemberDao
    @Autowired
    private lateinit var bandDao: BandDao
    @Autowired
    private lateinit var albumDao: AlbumDao
    @Autowired
    private lateinit var musicDao: MusicDao
    fun CreateAll() {
        var metal = Genre("1", "Metal")
        genreDao.save(metal)
        var member1 = Member("1", "Robert","Halford","Metal God")
        memberDao.save(member1)
        var member2 = Member("2","Glenn","Tipton")
        memberDao.save(member2)
        var member3 = Member("3","Richie","faulkner", "Falcon faulkner")
        memberDao.save(member3)
        var member4 = Member("4","Ian","Hill")
        memberDao.save(member4)
        var member5 = Member("5","Scott","Travis")
        memberDao.save(member5)
        val memberList : MutableList<Member> = mutableListOf<Member>()
        memberList.add(member1)
        memberList.add(member2)
        memberList.add(member3)
        memberList.add(member4)
        memberList.add(member5)
        var band = Band("1", "Judas Priest",memberList)
        bandDao.save(band)
        albumDao.save(Album("1","Painkiller","1990"))
        albumDao.save(Album("2","Angel of Retribution", "2005"))
        albumDao.save(Album("3", "Ram it down", "1988"))
        musicDao.save(Music("1","Leather rebel", band,metal,Album("1","Painkiller","1990")))
    }
}