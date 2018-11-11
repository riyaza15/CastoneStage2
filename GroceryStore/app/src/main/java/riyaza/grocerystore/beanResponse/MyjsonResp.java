package riyaza.grocerystore.beanResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyjsonResp {

    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("Detail")
    @Expose
    private List<Detail> detail = null;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }


    public class Response {

        @SerializedName("ResponseCode")
        @Expose
        private Integer responseCode;
        @SerializedName("ResponseText")
        @Expose
        private String responseText;

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseText() {
            return responseText;
        }

        public void setResponseText(String responseText) {
            this.responseText = responseText;
        }

    }
    public class GetBlockedTiming {

        @SerializedName("DateOfSlot")
        @Expose
        private String dateOfSlot;
        @SerializedName("AvailableTimeSlots")
        @Expose
        private Object availableTimeSlots;
        @SerializedName("ScheduleId")
        @Expose
        private Integer scheduleId;
        @SerializedName("GetBlockedTimings")
        @Expose
        private Object getBlockedTimings;
        @SerializedName("BlockId")
        @Expose
        private Integer blockId;
        @SerializedName("fkTimeId")
        @Expose
        private Integer fkTimeId;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("fkScheduledId")
        @Expose
        private Integer fkScheduledId;
        @SerializedName("utcDateOfSlot")
        @Expose
        private String utcDateOfSlot;

        public String getDateOfSlot() {
            return dateOfSlot;
        }

        public void setDateOfSlot(String dateOfSlot) {
            this.dateOfSlot = dateOfSlot;
        }

        public Object getAvailableTimeSlots() {
            return availableTimeSlots;
        }

        public void setAvailableTimeSlots(Object availableTimeSlots) {
            this.availableTimeSlots = availableTimeSlots;
        }

        public Integer getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(Integer scheduleId) {
            this.scheduleId = scheduleId;
        }

        public Object getGetBlockedTimings() {
            return getBlockedTimings;
        }

        public void setGetBlockedTimings(Object getBlockedTimings) {
            this.getBlockedTimings = getBlockedTimings;
        }

        public Integer getBlockId() {
            return blockId;
        }

        public void setBlockId(Integer blockId) {
            this.blockId = blockId;
        }

        public Integer getFkTimeId() {
            return fkTimeId;
        }

        public void setFkTimeId(Integer fkTimeId) {
            this.fkTimeId = fkTimeId;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Integer getFkScheduledId() {
            return fkScheduledId;
        }

        public void setFkScheduledId(Integer fkScheduledId) {
            this.fkScheduledId = fkScheduledId;
        }

        public String getUtcDateOfSlot() {
            return utcDateOfSlot;
        }

        public void setUtcDateOfSlot(String utcDateOfSlot) {
            this.utcDateOfSlot = utcDateOfSlot;
        }

    }
    public class Detail {

        @SerializedName("DateOfSlot")
        @Expose
        private String dateOfSlot;
        @SerializedName("AvailableTimeSlots")
        @Expose
        private List<AvailableTimeSlot> availableTimeSlots = null;
        @SerializedName("ScheduleId")
        @Expose
        private Integer scheduleId;
        @SerializedName("GetBlockedTimings")
        @Expose
        private List<GetBlockedTiming> getBlockedTimings = null;
        @SerializedName("BlockId")
        @Expose
        private Integer blockId;
        @SerializedName("fkTimeId")
        @Expose
        private Integer fkTimeId;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("fkScheduledId")
        @Expose
        private Integer fkScheduledId;
        @SerializedName("utcDateOfSlot")
        @Expose
        private String utcDateOfSlot;

        public String getDateOfSlot() {
            return dateOfSlot;
        }

        public void setDateOfSlot(String dateOfSlot) {
            this.dateOfSlot = dateOfSlot;
        }

        public List<AvailableTimeSlot> getAvailableTimeSlots() {
            return availableTimeSlots;
        }

        public void setAvailableTimeSlots(List<AvailableTimeSlot> availableTimeSlots) {
            this.availableTimeSlots = availableTimeSlots;
        }

        public Integer getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(Integer scheduleId) {
            this.scheduleId = scheduleId;
        }

        public List<GetBlockedTiming> getGetBlockedTimings() {
            return getBlockedTimings;
        }

        public void setGetBlockedTimings(List<GetBlockedTiming> getBlockedTimings) {
            this.getBlockedTimings = getBlockedTimings;
        }

        public Integer getBlockId() {
            return blockId;
        }

        public void setBlockId(Integer blockId) {
            this.blockId = blockId;
        }

        public Integer getFkTimeId() {
            return fkTimeId;
        }

        public void setFkTimeId(Integer fkTimeId) {
            this.fkTimeId = fkTimeId;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Integer getFkScheduledId() {
            return fkScheduledId;
        }

        public void setFkScheduledId(Integer fkScheduledId) {
            this.fkScheduledId = fkScheduledId;
        }

        public String getUtcDateOfSlot() {
            return utcDateOfSlot;
        }

        public void setUtcDateOfSlot(String utcDateOfSlot) {
            this.utcDateOfSlot = utcDateOfSlot;
        }

    }
    public class AvailableTimeSlot {

        @SerializedName("TimeSlotId")
        @Expose
        private Integer timeSlotId;
        @SerializedName("TimeOfSlot")
        @Expose
        private String timeOfSlot;
        @SerializedName("TimeofSlotDateTime")
        @Expose
        private String timeofSlotDateTime;

        public Integer getTimeSlotId() {
            return timeSlotId;
        }

        public void setTimeSlotId(Integer timeSlotId) {
            this.timeSlotId = timeSlotId;
        }

        public String getTimeOfSlot() {
            return timeOfSlot;
        }

        public void setTimeOfSlot(String timeOfSlot) {
            this.timeOfSlot = timeOfSlot;
        }

        public String getTimeofSlotDateTime() {
            return timeofSlotDateTime;
        }

        public void setTimeofSlotDateTime(String timeofSlotDateTime) {
            this.timeofSlotDateTime = timeofSlotDateTime;
        }

    }

}