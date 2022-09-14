package com.example.taehaed.Model


import com.example.taehaed.Constans.getValue
import com.example.taehaed.Model.ListenersForRespone.LoginStatus
import com.example.taehaed.Pojo.LogIn.LoginRoot
import com.example.taehaed.data.TaahiedImplements
import com.example.taehaed.Pojo.Index.IndexRoot
import com.example.taehaed.Pojo.home.HomeRoot
import com.example.taehaed.Pojo.Request.RequsetRoot
import com.example.taehaed.Pojo.FormReuest.FormRoot
import com.example.taehaed.Pojo.LogoOut.StatusRoot
import com.example.taehaed.Pojo.NoteToShow.NotesRoot
import com.example.taehaed.Pojo.NoteBodey
import com.example.taehaed.Pojo.FormReuest.FormData
import com.example.taehaed.Pojo.UserData
import io.reactivex.rxjava3.core.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import java.io.File

class Resportry {

    fun InsertLogin(userData: UserData?, loginStatus: LoginStatus?): Call<LoginRoot> {
        return TaahiedImplements.getInstanse().InsertLogin(userData)
    }

    fun geIndexRootCall(): Call<IndexRoot> {
        return TaahiedImplements.getInstanse().index
    }

    val homeRute: Call<HomeRoot>
        get() = TaahiedImplements.getInstanse().homeRute
    val currentIndexs: Call<IndexRoot>
        get() = TaahiedImplements.getInstanse().currentIndexs
    val newIndex: Call<IndexRoot>
        get() = TaahiedImplements.getInstanse().newIndex

    fun getRequsetRoot(clientId: Int): Call<RequsetRoot> {
        return TaahiedImplements.getInstanse().getRequsetRoot(clientId)
    }

    fun getOperationRequsetRoot(request_service_id: Int): Call<FormRoot> {
        return TaahiedImplements.getInstanse().getOperationRequsetRoot(request_service_id)
    }

    fun DeletUserToken(): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().DeletUserToken()
    }

    fun getNotes(request_service_id: Int): Call<NotesRoot> {
        return TaahiedImplements.getInstanse().getNotes(request_service_id)
    }

    fun CancelRequstNote(noteBodey: NoteBodey?): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().CancelRequstNote(noteBodey)
    }

    fun ConverNoteToAccept(request_service_id: Int): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().ConverNoteToAccept(request_service_id)
    }

    fun ConverServiersAceptToCanel(noteBodey: NoteBodey?): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().ConverServiersAceptToCanel(noteBodey)
    }

    fun setNotes(noteBodey: NoteBodey?): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().setNotes(noteBodey)
    }

    fun setDoneservies(formData: FormData?): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().setDoneservies(formData)
    }

    fun ConvertDoneToAccept(noteBodey: NoteBodey?): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().ConvertDoneToAccept(noteBodey)
    }

    fun ObesrveNotes(request_service_id: Int): Observable<NotesRoot> {
        return TaahiedImplements.getInstanse().ObesrveNotes(request_service_id)
    }



    fun setDoneserviesWithFiels(
        formdata: FormData, result_attached_utilities_receipt: List<File>? = null,
        result_attached_husband_national_id: List<File>? = null,
        vehicle_result_attachments_national_id: List<File>? = null,
        vehicle_result_attachments_driving_license: List<File>? = null,
        vehicle_result_attachments_vehicle_license: List<File>? = null,
        vehicle_result_attachments_purchase_contract: List<File>? = null,
        business_result_attachment_owner: List<File>? = null,
        business_result_attachment_amenities_receipt: List<File>? = null,
        business_result_attachment_commercial_record: List<File>? = null,
        business_result_attachment_tax_card: List<File>? = null,
        business_result_attachment_activity_license: List<File>? = null,
        business_result_attachment_partner_national_id: List<File>? = null,
        home_activity_result_attachments_owner: List<File>? = null,
        home_activity_result_attachments_amenities_receipt: List<File>? = null,
        home_activity_result_attachments_supplier_invoices: List<File>? = null,
        home_activity_result_attachments_sales_statements: List<File>? = null,

        service_activity_result_attachments_owner: List<File>? = null,
        service_activity_result_attachments_amenities_receipt: List<File>? = null,

        attachments: List<File>? = null): Call<StatusRoot> {
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            addFormDataPart("request_service_id", getValue(formdata.request_service_id))
            addFormDataPart("client_name", getValue(formdata.client_name))
            addFormDataPart("longitude", getValue(formdata.longitude))
            addFormDataPart("latitude", getValue(formdata.latitude))
            addFormDataPart("client_nickname", getValue(formdata.client_nickname))
            addFormDataPart("client_national_id", getValue(formdata.client_national_id))
            addFormDataPart("client_birth_date", getValue(formdata.client_birth_date))
            addFormDataPart(
                "residence_accommodation_type",
                getValue(formdata.residence_accommodation_type)
            )
            addFormDataPart(
                "client_mobile_number_1",
                getValue(formdata.client_mobile_number_1)
            )
            addFormDataPart(
                "client_mobile_number_2",
                getValue(formdata.client_mobile_number_2)
            )
            addFormDataPart("client_phone", getValue(formdata.client_phone))
            addFormDataPart(
                "residence_rent_duration",
                getValue(formdata.residence_rent_duration)
            )
            addFormDataPart(
                "residence_rent_value",
                getValue(formdata.residence_rent_value)
            )
            addFormDataPart("residence_rent_to", getValue(formdata.residence_rent_to))
            addFormDataPart(
                "residence_building_number",
                getValue(formdata.residence_building_number)
            )
            addFormDataPart(
                "residence_street_name",
                getValue(formdata.residence_street_name)
            )
            addFormDataPart(
                "residence_neighborhood",
                getValue(formdata.residence_neighborhood)
            )
            addFormDataPart("residence_city", getValue(formdata.residence_city))
            addFormDataPart(
                "residence_governorate",
                getValue(formdata.residence_governorate)
            )
            addFormDataPart(
                "residence_special_marque",
                getValue(formdata.residence_special_marque)
            )
            addFormDataPart(
                "residence_stay_duration",
                getValue(formdata.residence_stay_duration)
            )
            addFormDataPart("attached_type", getValue(formdata.attached_type))
            addFormDataPart(
                "attached_counter_number",
                getValue(formdata.attached_counter_number)
            )
            addFormDataPart(
                "attached_plate_number",
                getValue(formdata.attached_plate_number)
            )
            addFormDataPart(
                "attached_average_monthly_consumption",
                getValue(formdata.attached_average_monthly_consumption)
            )
            addFormDataPart(
                "attached_average_beneficiary_name",
                getValue(formdata.attached_average_beneficiary_name)
            )
            addFormDataPart("husband_name", getValue(formdata.husband_name))
            addFormDataPart("husband_nickname", getValue(formdata.husband_nickname))
            addFormDataPart("husband_national_id", getValue(formdata.husband_national_id))
            addFormDataPart("husband_birth_date", getValue(formdata.husband_birth_date))
            addFormDataPart(
                "husband_mobile_number_1",
                getValue(formdata.husband_mobile_number_1)
            )
            addFormDataPart(
                "husband_mobile_number_2",
                getValue(formdata.husband_mobile_number_2)
            )
            addFormDataPart("husband_phone", getValue(formdata.husband_phone))
            addFormDataPart(
                "husband_family_number",
                getValue(formdata.husband_family_number)
            )
            addFormDataPart(
                "result_personal_data",
                getValue(formdata.result_personal_data)
            )
            addFormDataPart(
                "result_residence_data",
                getValue(formdata.result_residence_data)
            )
            addFormDataPart(
                "result_client_with_same_address",
                getValue(formdata.result_client_with_same_address)
            )
            addFormDataPart(
                "result_attachment_data",
                getValue(formdata.result_attachment_data)
            )
            addFormDataPart("result_husband_data", getValue(formdata.result_husband_data))
            addFormDataPart("result_family_data", getValue(formdata.result_family_data))
            addFormDataPart(
                "result_client_reputation",
                getValue(formdata.result_client_reputation)
            )
            addFormDataPart("result_sources", getValue(formdata.result_sources))

            addFormDataPart("work_name", getValue(formdata.work_name))
            addFormDataPart(
                "work_building_number",
                getValue(formdata.work_building_number)
            )
            addFormDataPart("work_street_name", getValue(formdata.work_street_name))
            addFormDataPart("work_neighborhood", getValue(formdata.work_neighborhood))
            addFormDataPart("work_city", getValue(formdata.work_city))
            addFormDataPart("work_governorate", getValue(formdata.work_governorate))
            addFormDataPart("work_special_marque", getValue(formdata.work_special_marque))
            addFormDataPart("hr_name", getValue(formdata.hr_name))
            addFormDataPart("hr_job_title", getValue(formdata.hr_job_title))
            addFormDataPart("hr_total_income", getValue(formdata.hr_total_income))
            addFormDataPart("hr_insured", getValue(formdata.hr_insured))
            addFormDataPart("hr_work_committed", getValue(formdata.hr_work_committed))
            addFormDataPart(
                "work_result_employer_name",
                getValue(formdata.work_result_employer_name)
            )
            addFormDataPart(
                "work_result_employer_address",
                getValue(formdata.work_result_employer_address)
            )
            addFormDataPart("work_result_hr_meet", getValue(formdata.work_result_hr_meet))
            addFormDataPart(
                "work_result_job_title",
                getValue(formdata.work_result_job_title)
            )
            addFormDataPart("work_result_income", getValue(formdata.work_result_income))
            addFormDataPart("work_result_insured", getValue(formdata.work_result_insured))
            addFormDataPart(
                "work_result_customer_heard",
                getValue(formdata.work_result_customer_heard)
            )
            addFormDataPart(
                "driving_license_type",
                getValue(formdata.driving_license_type)
            )
            addFormDataPart(
                "driving_license_number",
                getValue(formdata.driving_license_number)
            )
            addFormDataPart(
                "driving_license_traffic_center",
                getValue(formdata.driving_license_traffic_center)
            )
            addFormDataPart(
                "driving_license_form",
                getValue(formdata.driving_license_form)
            )
            addFormDataPart("driving_license_to", getValue(formdata.driving_license_to))
            addFormDataPart(
                "vehicle_license_type",
                getValue(formdata.vehicle_license_type)
            )
            addFormDataPart(
                "vehicle_license_brand",
                getValue(formdata.vehicle_license_brand)
            )
            addFormDataPart(
                "vehicle_license_model",
                getValue(formdata.vehicle_license_model)
            )
            addFormDataPart(
                "vehicle_license_engine_capacity",
                getValue(formdata.vehicle_license_engine_capacity)
            )
            addFormDataPart(
                "vehicle_license_manufacturing_year",
                getValue(formdata.vehicle_license_manufacturing_year)
            )
            addFormDataPart(
                "vehicle_license_traveled_distance",
                getValue(formdata.vehicle_license_traveled_distance)
            )
            addFormDataPart(
                "vehicle_license_form",
                getValue(formdata.vehicle_license_form)
            )
            addFormDataPart("vehicle_license_to", getValue(formdata.vehicle_license_to))
            addFormDataPart(
                "vehicle_license_situation",
                getValue(formdata.vehicle_license_situation)
            )
            addFormDataPart(
                "vehicle_license_value_when_buying",
                getValue(formdata.vehicle_license_value_when_buying)
            )
            addFormDataPart(
                "vehicle_license_value_now",
                getValue(formdata.vehicle_license_value_now)
            )
            addFormDataPart(
                "vehicle_result_driving_license",
                getValue(formdata.vehicle_result_driving_license)
            )
            addFormDataPart(
                "vehicle_result_license",
                getValue(formdata.vehicle_result_license)
            )
            addFormDataPart(
                "vehicle_result_condition",
                getValue(formdata.vehicle_result_condition)
            )
            addFormDataPart(
                "vehicle_result_value",
                getValue(formdata.vehicle_result_value)
            )
            addFormDataPart(
                "vehicle_result_sources",
                getValue(formdata.vehicle_result_sources)
            )




            addFormDataPart("business_name", getValue(formdata.business_name))
            addFormDataPart(
                "business_common_name",
                getValue(formdata.business_common_name)
            )
            addFormDataPart("business_start_date", getValue(formdata.business_start_date))
            addFormDataPart("business_type", getValue(formdata.business_type))
            addFormDataPart(
                "business_branches_number",
                getValue(formdata.business_branches_number)
            )
            addFormDataPart(
                "business_workers_number",
                getValue(formdata.business_workers_number)
            )
            addFormDataPart("business_partner", getValue(formdata.business_partner))
            addFormDataPart(
                "business_partner_number",
                getValue(formdata.business_partner_number)
            )
            addFormDataPart(
                "business_customer_share",
                getValue(formdata.business_customer_share)
            )
            addFormDataPart("business_gets_paid", getValue(formdata.business_gets_paid))
            addFormDataPart(
                "business_purpose_funding",
                getValue(formdata.business_purpose_funding)
            )
            addFormDataPart(
                "commercial_registration_No",
                getValue(formdata.commercial_registration_No)
            )
            addFormDataPart(
                "commercial_form_date",
                getValue(formdata.commercial_form_date)
            )
            addFormDataPart("commercial_to_date", getValue(formdata.commercial_to_date))
            addFormDataPart(
                "commercial_tax_card_number",
                getValue(formdata.commercial_tax_card_number)
            )
            addFormDataPart(
                "commercial_Taxes_errand",
                getValue(formdata.commercial_Taxes_errand)
            )
            addFormDataPart(
                "headquarters_possession_type",
                getValue(formdata.headquarters_possession_type)
            )
            addFormDataPart(
                "headquarters_owner_name",
                getValue(formdata.headquarters_owner_name)
            )
            addFormDataPart(
                "headquarters_client_relationship",
                getValue(formdata.headquarters_client_relationship)
            )
            addFormDataPart("headquarters_to", getValue(formdata.headquarters_to))
            addFormDataPart(
                "headquarters_building_number",
                getValue(formdata.headquarters_building_number)
            )
            addFormDataPart(
                "headquarters_street_name",
                getValue(formdata.headquarters_street_name)
            )
            addFormDataPart(
                "headquarters_neighborhood",
                getValue(formdata.headquarters_neighborhood)
            )
            addFormDataPart("headquarters_city", getValue(formdata.headquarters_city))
            addFormDataPart(
                "headquarters_governorate",
                getValue(formdata.headquarters_governorate)
            )
            addFormDataPart(
                "headquarters_special_marque",
                getValue(formdata.headquarters_special_marque)
            )
            addFormDataPart("headquarters_area", getValue(formdata.headquarters_area))
            addFormDataPart("activity_owner_name", getValue(formdata.activity_owner_name))
            addFormDataPart(
                "activity_owner_nickname",
                getValue(formdata.activity_owner_nickname)
            )
            addFormDataPart(
                "activity_owner_national_ID",
                getValue(formdata.activity_owner_national_ID)
            )
            addFormDataPart(
                "activity_owner_another_job",
                getValue(formdata.activity_owner_another_job)
            )
            addFormDataPart(
                "activity_owner_employment_type",
                getValue(formdata.activity_owner_employment_type)
            )
            addFormDataPart(
                "activity_owner_average_income",
                getValue(formdata.activity_owner_average_income)
            )
            addFormDataPart(
                "activity_owner_family_number",
                getValue(formdata.activity_owner_family_number)
            )
            addFormDataPart(
                "activity_manager_name",
                getValue(formdata.activity_manager_name)
            )
            addFormDataPart(
                "activity_manager_nickname",
                getValue(formdata.activity_manager_nickname)
            )
            addFormDataPart(
                "activity_manager_national_ID",
                getValue(formdata.activity_manager_national_ID)
            )
            addFormDataPart(
                "activity_manager_another_job",
                getValue(formdata.activity_manager_another_job)
            )
            addFormDataPart(
                "activity_manager_employment_type",
                getValue(formdata.activity_manager_employment_type)
            )
            addFormDataPart(
                "activity_manager_average_income",
                getValue(formdata.activity_manager_average_income)
            )
            addFormDataPart(
                "activity_manager_owner_relationship",
                getValue(formdata.activity_manager_owner_relationship)
            )
            addFormDataPart(
                "activity_manager_activity_duration",
                getValue(formdata.activity_manager_activity_duration)
            )
            addFormDataPart(
                "activity_manager_shift_type",
                getValue(formdata.activity_manager_shift_type)
            )
            addFormDataPart(
                "activity_manager_mobile_number_1",
                getValue(formdata.activity_manager_mobile_number_1)
            )
            addFormDataPart(
                "activity_manager_mobile_number_2",
                getValue(formdata.activity_manager_mobile_number_2)
            )
            addFormDataPart(
                "business_result_activity",
                getValue(formdata.business_result_activity)
            )
            addFormDataPart(
                "business_result_headquarters",
                getValue(formdata.business_result_headquarters)
            )
            addFormDataPart(
                "business_result_keeping_appointment",
                getValue(formdata.business_result_keeping_appointment)
            )
            addFormDataPart(
                "business_result_owner_data",
                getValue(formdata.business_result_owner_data)
            )
            addFormDataPart(
                "business_result_administrator_data",
                getValue(formdata.business_result_administrator_data)
            )
            addFormDataPart(
                "business_result_ustomer_heard",
                getValue(formdata.business_result_ustomer_heard)
            )
            addFormDataPart(
                "business_result_origin_reputation",
                getValue(formdata.business_result_origin_reputation)
            )
            addFormDataPart(
                "business_result_sources",
                getValue(formdata.business_result_sources)
            )

            addFormDataPart("home_activity_type", getValue(formdata.home_activity_type))
            addFormDataPart("home_activity_area", getValue(formdata.home_activity_area))
            addFormDataPart(
                "home_activity_front_room_1",
                getValue(formdata.home_activity_front_room_1)
            )
            addFormDataPart(
                "home_activity_separate_door",
                getValue(formdata.home_activity_separate_door)
            )
            addFormDataPart(
                "home_activity_front_room_2",
                getValue(formdata.home_activity_front_room_2)
            )
            addFormDataPart(
                "home_activity_purpose_financing",
                getValue(formdata.home_activity_purpose_financing)
            )
            addFormDataPart(
                "home_activity_capital",
                getValue(formdata.home_activity_capital)
            )
            addFormDataPart("sales_data_manager", getValue(formdata.sales_data_manager))
            addFormDataPart("sales_data_how_sell", getValue(formdata.sales_data_how_sell))
            addFormDataPart(
                "sales_data_customers_payment_method",
                getValue(formdata.sales_data_customers_payment_method)
            )
            addFormDataPart(
                "sales_data_customers_pay",
                getValue(formdata.sales_data_customers_pay)
            )
            addFormDataPart(
                "sales_data_suppliers_type",
                getValue(formdata.sales_data_suppliers_type)
            )
            addFormDataPart(
                "sales_data_suppliers_payment_method",
                getValue(formdata.sales_data_suppliers_payment_method)
            )
            addFormDataPart(
                "home_activity_result_owner",
                getValue(formdata.home_activity_result_owner)
            )
            addFormDataPart(
                "home_activity_result_headquarters",
                getValue(formdata.home_activity_result_headquarters)
            )
            addFormDataPart(
                "home_activity_result_manager",
                getValue(formdata.home_activity_result_manager)
            )
            addFormDataPart(
                "home_activity_result_customer_heard",
                getValue(formdata.home_activity_result_customer_heard)
            )
            addFormDataPart(
                "home_activity_result_sources",
                getValue(formdata.home_activity_result_sources)
            )

            addFormDataPart(
                "service_activity_type",
                getValue(formdata.service_activity_type)
            )
            addFormDataPart(
                "service_activity_from",
                getValue(formdata.service_activity_from)
            )
            addFormDataPart(
                "service_activity_workers_number",
                getValue(formdata.service_activity_workers_number)
            )
            addFormDataPart(
                "service_activity_working_hours",
                getValue(formdata.service_activity_working_hours)
            )
            addFormDataPart(
                "service_activity_headquarters_1",
                getValue(formdata.service_activity_headquarters_1)
            )
            addFormDataPart(
                "service_activity_financing_coverage",
                getValue(formdata.service_activity_financing_coverage)
            )
            addFormDataPart(
                "service_activity_headquarters_2",
                getValue(formdata.service_activity_headquarters_2)
            )
            addFormDataPart(
                "service_activity_purpose_funding",
                getValue(formdata.service_activity_purpose_funding)
            )
            addFormDataPart(
                "service_activity_weekly_rest",
                getValue(formdata.service_activity_weekly_rest)
            )
            addFormDataPart(
                "service_activity_another_job",
                getValue(formdata.service_activity_another_job)
            )
            addFormDataPart(
                "service_activity_work_type",
                getValue(formdata.service_activity_work_type)
            )
            addFormDataPart(
                "service_activity_average_income",
                getValue(formdata.service_activity_average_income)
            )
            addFormDataPart(
                "service_activity_family_number",
                getValue(formdata.service_activity_family_number)
            )
            addFormDataPart(
                "service_activity_result_owner",
                getValue(formdata.service_activity_result_owner)
            )
            addFormDataPart(
                "service_activity_result_headquarters",
                getValue(formdata.service_activity_result_headquarters)
            )
            addFormDataPart(
                "service_activity_result_manager",
                getValue(formdata.service_activity_result_manager)
            )
            addFormDataPart(
                "service_activity_result_tools",
                getValue(formdata.service_activity_result_tools)
            )
            addFormDataPart(
                "service_activity_result_lying_down",
                getValue(formdata.service_activity_result_lying_down)
            )
            addFormDataPart(
                "service_activity_result_customer_heard",
                getValue(formdata.service_activity_result_customer_heard)
            )
            addFormDataPart(
                "service_activity_result_sources",
                getValue(formdata.service_activity_result_sources)
            )

            addFormDataPart("property_type", getValue(formdata.property_type))
            addFormDataPart("property_name", getValue(formdata.property_name))
            addFormDataPart("property_build_date", getValue(formdata.property_build_date))
            addFormDataPart("property_number", getValue(formdata.property_number))
            addFormDataPart(
                "property_street_name",
                getValue(formdata.property_street_name)
            )
            addFormDataPart(
                "property_neighborhood",
                getValue(formdata.property_neighborhood)
            )
            addFormDataPart("property_city", getValue(formdata.property_city))
            addFormDataPart(
                "property_governorate",
                getValue(formdata.property_governorate)
            )
            addFormDataPart(
                "property_special_marque",
                getValue(formdata.property_special_marque)
            )
            addFormDataPart("property_area", getValue(formdata.property_area))
            addFormDataPart("property_status", getValue(formdata.property_status))
            addFormDataPart(
                "property_floor_number",
                getValue(formdata.property_floor_number)
            )
            addFormDataPart(
                "property_apartment_number",
                getValue(formdata.property_apartment_number)
            )
            addFormDataPart(
                "property_facade_dimension",
                getValue(formdata.property_facade_dimension)
            )
            addFormDataPart(
                "property_result_client_data",
                getValue(formdata.property_result_client_data)
            )
            addFormDataPart(
                "property_result_husband",
                getValue(formdata.property_result_husband)
            )
            addFormDataPart(
                "property_result_employer_data",
                getValue(formdata.property_result_employer_data)
            )
            addFormDataPart(
                "property_result_income",
                getValue(formdata.property_result_income)
            )
            addFormDataPart(
                "property_result_insured",
                getValue(formdata.property_result_insured)
            )
            addFormDataPart(
                "property_result_property_data",
                getValue(formdata.property_result_property_data)
            )
            addFormDataPart(
                "property_result_condition",
                getValue(formdata.property_result_condition)
            )
            addFormDataPart(
                "property_result_sources",
                getValue(formdata.property_result_sources)
            )
            addFormDataPart(
                "property_result_attachments",
                getValue(formdata.property_result_attachments)
            )
            addFormDataPart(
                "activity_supplier_name",
                getValue(formdata.activity_supplier_name)
            )
            addFormDataPart(
                "activity_supplier_nickname",
                getValue(formdata.activity_supplier_nickname)
            )
            addFormDataPart(
                "activity_supplier_form_date",
                getValue(formdata.activity_supplier_form_date)
            )
            addFormDataPart(
                "activity_supplier_to_type",
                getValue(formdata.activity_supplier_to_type)
            )
            addFormDataPart(
                "activity_supplier_branches_number",
                getValue(formdata.activity_supplier_branches_number)
            )
            addFormDataPart(
                "activity_supplier_workers_number",
                getValue(formdata.activity_supplier_workers_number)
            )
            addFormDataPart(
                "activity_supplier_partners",
                getValue(formdata.activity_supplier_partners)
            )
            addFormDataPart(
                "activity_supplier_partners_number",
                getValue(formdata.activity_supplier_partners_number)
            )
            addFormDataPart(
                "activity_supplier_customer_share",
                getValue(formdata.activity_supplier_customer_share)
            )
            addFormDataPart(
                "activity_supplier_gets_paid",
                getValue(formdata.activity_supplier_gets_paid)
            )
            addFormDataPart(
                "activity_supplier_wage_value",
                getValue(formdata.activity_supplier_wage_value)
            )
            addFormDataPart(
                "activity_supplier_purpose_funding",
                getValue(formdata.activity_supplier_purpose_funding)
            )
            addFormDataPart(
                "supplier_result_delivery_time",
                getValue(formdata.supplier_result_delivery_time)
            )
            addFormDataPart(
                "supplier_result_net_price",
                getValue(formdata.supplier_result_net_price)
            )
            addFormDataPart(
                "supplier_result_quality_level",
                getValue(formdata.supplier_result_quality_level)
            )
            addFormDataPart(
                "supplier_result_ability",
                getValue(formdata.supplier_result_ability)
            )
            addFormDataPart(
                "supplier_result_geographical_location",
                getValue(formdata.supplier_result_geographical_location)
            )
            addFormDataPart(
                "supplier_result_possibilities",
                getValue(formdata.supplier_result_possibilities)
            )
            addFormDataPart(
                "supplier_result_management",
                getValue(formdata.supplier_result_management)
            )
            addFormDataPart(
                "supplier_result_reputation",
                getValue(formdata.supplier_result_reputation)
            )
            addFormDataPart(
                "supplier_result_financial_situation",
                getValue(formdata.supplier_result_financial_situation)
            )
            addFormDataPart(
                "supplier_result_previous_performance",
                getValue(formdata.supplier_result_previous_performance)
            )
            addFormDataPart(
                "supplier_result_operations_monitor",
                getValue(formdata.supplier_result_operations_monitor)
            )
            addFormDataPart(
                "supplier_result_training",
                getValue(formdata.supplier_result_training)
            )
            addFormDataPart(
                "supplier_result_social_relations",
                getValue(formdata.supplier_result_social_relations)
            )
            addFormDataPart(
                "supplier_result_communication_system",
                getValue(formdata.supplier_result_communication_system)
            )
            addFormDataPart(
                "supplier_result_impression",
                getValue(formdata.supplier_result_impression)
            )
            addFormDataPart(
                "supplier_result_do_work",
                getValue(formdata.supplier_result_do_work)
            )
            addFormDataPart(
                "supplier_result_business_volume",
                getValue(formdata.supplier_result_business_volume)
            )
            addFormDataPart(
                "note",
                getValue(formdata.note)
            )

            // my files are List<ByteArray>, okhttp has a few utility methods like .toRequestBody for various types like below
            if (result_attached_utilities_receipt != null) {
                result_attached_utilities_receipt.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "result_attached_utilities_receipt[]",
                        "${result_attached_utilities_receipt.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )
                 //   Log.d("Aboud", "fsetDoneserviesWithFielsName: "+${result_attached_utilities_receipt.get(index))
                }
            }
            if (result_attached_husband_national_id != null) {
                result_attached_husband_national_id.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "result_attached_husband_national_id[]",
                        "${result_attached_husband_national_id.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (vehicle_result_attachments_national_id != null) {
                vehicle_result_attachments_national_id.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "vehicle_result_attachments_national_id[]",
                        "${vehicle_result_attachments_national_id.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (vehicle_result_attachments_driving_license != null) {
                vehicle_result_attachments_driving_license.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "vehicle_result_attachments_driving_license[]",
                        "${vehicle_result_attachments_driving_license.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (vehicle_result_attachments_vehicle_license != null) {
                vehicle_result_attachments_vehicle_license.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "vehicle_result_attachments_vehicle_license[]",
                        "${vehicle_result_attachments_vehicle_license.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (vehicle_result_attachments_purchase_contract != null) {
                vehicle_result_attachments_purchase_contract.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "vehicle_result_attachments_purchase_contract[]",
                        "${vehicle_result_attachments_purchase_contract.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (business_result_attachment_owner != null) {
                business_result_attachment_owner.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "business_result_attachment_owner[]",
                        "${business_result_attachment_owner.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (business_result_attachment_amenities_receipt != null) {
                business_result_attachment_amenities_receipt.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "business_result_attachment_amenities_receipt[]",
                        "${business_result_attachment_amenities_receipt.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (business_result_attachment_commercial_record != null) {
                business_result_attachment_commercial_record.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "business_result_attachment_commercial_record[]",
                        "${business_result_attachment_commercial_record.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (business_result_attachment_tax_card != null) {
                business_result_attachment_tax_card.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        " business_result_attachment_tax_card[]",
                        "${business_result_attachment_tax_card.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (business_result_attachment_activity_license != null) {
                business_result_attachment_activity_license.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "business_result_attachment_activity_license[]",
                        "${business_result_attachment_activity_license.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (business_result_attachment_partner_national_id != null) {
                business_result_attachment_partner_national_id.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "business_result_attachment_partner_national_id[]",
                        "${business_result_attachment_partner_national_id.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (home_activity_result_attachments_owner != null) {
                home_activity_result_attachments_owner.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "home_activity_result_attachments_owner[]",
                        "${home_activity_result_attachments_owner.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (home_activity_result_attachments_amenities_receipt != null) {
                home_activity_result_attachments_amenities_receipt.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "home_activity_result_attachments_amenities_receipt[]",
                        "${home_activity_result_attachments_amenities_receipt.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (home_activity_result_attachments_supplier_invoices != null) {
                home_activity_result_attachments_supplier_invoices.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "home_activity_result_attachments_supplier_invoices[]",
                        "${home_activity_result_attachments_supplier_invoices.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (home_activity_result_attachments_sales_statements != null) {
                home_activity_result_attachments_sales_statements.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "home_activity_result_attachments_sales_statements[]",
                        "${home_activity_result_attachments_sales_statements.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }

            if (service_activity_result_attachments_owner != null) {
                service_activity_result_attachments_owner.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "service_activity_result_attachments_owner[]",
                        "${service_activity_result_attachments_owner.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (service_activity_result_attachments_amenities_receipt != null) {
                service_activity_result_attachments_amenities_receipt.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "service_activity_result_attachments_amenities_receipt[]",
                        "${service_activity_result_attachments_amenities_receipt.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
            if (attachments != null) {
                attachments.forEachIndexed { index, bytes ->
                    addFormDataPart(
                        "attachments[]",
                        "${attachments.get(index)}",
                        bytes.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )

                }
            }
        }.build()
        return TaahiedImplements.getInstanse().setDoneserviesWithFiels2(requestBody)
    }

    fun setDoneserviesWithFiels3(params: Map<String?, RequestBody?>?): Call<StatusRoot> {
        return TaahiedImplements.getInstanse().setDoneserviesWithFiels3(params)
    }



}