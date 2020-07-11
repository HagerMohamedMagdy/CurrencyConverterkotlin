package challengue.swensonhe.com.currencyconverter.model



import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class RateResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("rates")
	val rates: Map<String, Double>? = null,

	@field:SerializedName("timestamp")
	val timestamp: Int? = null,

	@field:SerializedName("base")
	val base: String? = null
)
data class Rate(val name: String,
				val value: Double) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readDouble()) {
	}
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(name)
		parcel.writeDouble(value)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Rate> {
		override fun createFromParcel(parcel: Parcel): Rate {
			return Rate(parcel)
		}

		override fun newArray(size: Int): Array<Rate?> {
			return arrayOfNulls(size)
		}
	}}