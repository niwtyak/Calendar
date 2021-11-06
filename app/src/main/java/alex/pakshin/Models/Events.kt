package alex.pakshin.Models

import com.vicpin.krealmextensions.AutoIncrementPK
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
@AutoIncrementPK
open class Events(
    @PrimaryKey var id: Long? = null,
    var title: String = "",
    var date: Date? = null,
    var time_start: String? = null,
    var time_end: String? = null,
    var description: String = ""
) : RealmObject()
