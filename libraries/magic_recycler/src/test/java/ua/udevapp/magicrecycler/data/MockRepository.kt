package ua.udevapp.magicrecycler.data

object MockRepository {
    fun getInitialMockData(): List<Pair<Int, String>> = listOf(
        1 to "First",
        2 to "Second",
        3 to "Third",
        4 to "Fourth",
        5 to "Fifth",
        6 to "Sixth",
        7 to "Seventh",
        8 to "Eighth",
        9 to "Ninth",
        10 to "Tenth"
    )

    fun getPartialChangedMockData(): List<Pair<Int, String>> = listOf(
        3 to "Third v2",
        5 to "Fifth v2",
        7 to "Seventh v2",
        9 to "Ninth v2"
    )

    fun getUpdatedItemFromMockData() = 4 to "Fourth v2"

    fun getUpdatedMockData(): List<Pair<Int, String>> = listOf(
        1 to "First",
        2 to "Second",
        3 to "Third v2",
        4 to "Fourth",
        5 to "Fifth v2",
        6 to "Sixth",
        7 to "Seventh v2",
        8 to "Eighth",
        9 to "Ninth v2",
        10 to "Tenth"
    )
}