package com.goforer.myhomework.data.source.model.entity.cardinfo

import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.card.response.CardInfo
import com.goforer.myhomework.data.source.model.entity.user.User

class CardInfoTest {
    companion object {
        val mock by lazy {
            CardInfoTest()
        }

        fun getInstance() = mock
    }

    val cardInfo0 = CardInfo(
        true,
        Card(
            0,
            "https://farm66.staticflickr.com/65535/50164930756_950fa07c9d.jpg",
            "Ten Outrageous Ideas For Your New Corvette | new corvette",
            1
        ),
        User(
            "aaaa",
            "a소개합니다.",
            0
        ),
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
        )
    )
}
