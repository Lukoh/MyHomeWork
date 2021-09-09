package com.goforer.myhomework.data.source.model.entity.home

import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.home.response.Feed
import com.goforer.myhomework.data.source.model.entity.home.response.Home
import com.goforer.myhomework.data.source.model.entity.user.User

class HomeTest {
    companion object {
        val mock by lazy {
            HomeTest()
        }

        fun getInstance() = mock
    }

    val cards = listOf(
        Card(
            0,
            "https://farm66.staticflickr.com/65535/50164032222_6cf736ec23.jpg",
            "Two Stain Glass Panels",
            70
        ),
        Card(
            0,
            "https://farm66.staticflickr.com/65535/50163247213_bd3d0da60c.jpg",
            "Last Judgement",
            69
        ),
        Card(
            0,
            "https://farm66.staticflickr.com/65535/50163816146_b64f80fdc4.jpg",
            "Hermitage Statue Gallery",
            68
        ),
        Card(
            0,
            "https://farm66.staticflickr.com/65535/50164077672_916cb20883.jpg",
            "India - Rajasthan - Jaisalmer - Patwa Havelis - View To Jaisalamer Fort - 74dd",
            67
        )
    )

    val feed0 = Feed(
        true,
        cards
    )

    val home0 = Home(
        true,
        listOf(
            Card(
                0,
                "https://farm66.staticflickr.com/65535/50164032222_6cf736ec23.jpg",
                "Two Stain Glass Panels",
                70
            ),
            Card(
                0,
                "https://farm66.staticflickr.com/65535/50163247213_bd3d0da60c.jpg",
                "Last Judgement",
                69
            ),
            Card(
                0,
                "https://farm66.staticflickr.com/65535/50163816146_b64f80fdc4.jpg",
                "Hermitage Statue Gallery",
                68
            ),
            Card(
                0,
                "https://farm66.staticflickr.com/65535/50164077672_916cb20883.jpg",
                "India - Rajasthan - Jaisalmer - Patwa Havelis - View To Jaisalamer Fort - 74dd",
                67
            )
        ),
        listOf(
            User(
                "aaaa",
                "a소개합니다.",
                0
            ),
            User(
                "bbb",
                "b소개합니다.",
                1
            ),
            User(
                "ccc",
                "c소개합니다.",
                2
            ),
            User(
                "ddd",
                "d소개합니다.",
                3
            ),
        )
    )
}
