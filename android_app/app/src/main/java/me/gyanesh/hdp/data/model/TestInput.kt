package me.gyanesh.hdp.data.model

import androidx.annotation.Keep

@Keep
data class TestInput(
    //patient info
    var patient_name: String? = null,
    var patient_id: String? = null,

    //numerical values
    var age: Int? = null,
    var trestbps: Float? = null,
    var chol: Float? = null,
    var thalach: Float? = null,
    var oldpeak: Float? = null,

    //categorical value
    var sex: Int? = null,
    var cp: Int? = null,
    var fbs: Int? = null,
    var restecg: Int? = null,
    var exang: Int? = null,
    var slope: Int? = null,
    var ca: Int? = null,
    var thal: Int? = null,
) {
    fun isValid(): Boolean {
        return patient_name != null
                && patient_id != null
                && age != null
                && trestbps != null
                && chol != null
                && thalach != null
                && oldpeak != null

                && sex != null
                && cp != null
                && fbs != null
                && restecg != null
                && exang != null
                && slope != null
                && ca != null
                && thal != null
    }
}