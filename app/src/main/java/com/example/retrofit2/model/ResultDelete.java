package com.example.retrofit2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultDelete {

        @SerializedName("success")
        @Expose
        private Success success;

        public Success getSuccess() {
            return success;
        }

        public void setSuccess(Success success) {
            this.success = success;
        }


}
