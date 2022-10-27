package io.tech4fun.lanorganizer.data.sources

import io.tech4fun.lanorganizer.data.models.LanModel

class LanDataSource {
    private var _lan: LanModel = LanModel("my_db_id", "My Awesome LAN", "Someday", "Somewhere")
    var latestLan : LanModel
        get() = _lan
        set(value) {_lan = value}
}