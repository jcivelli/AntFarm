
class TestFlipper(val value : CoinFlip) : CoinFlip.Flipper {
    override fun toss(): CoinFlip {
        return value
    }
}
