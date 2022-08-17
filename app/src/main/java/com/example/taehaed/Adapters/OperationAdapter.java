package com.example.taehaed.Adapters;


import static com.example.taehaed.Constans.setAlertMeaage;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.Request.RequestService;
import com.example.taehaed.Pojo.Request.RequsetRoot;
import com.example.taehaed.R;
import com.example.taehaed.Screens.Fragment.NotesAddsFragment;
import com.example.taehaed.Screens.Fragment.ServiersFragment;
import com.example.taehaed.Screens.OperartionQuset;
import com.example.taehaed.databinding.RequestServiesBinding;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.OparetaionHolder> {
    private RequsetRoot requsetRoot;
    private TaehaedVModel taehaedVModel;
    private AlertDialog alertDialog;

    public OperationAdapter(RequsetRoot requsetRoot, TaehaedVModel taehaedVModel) {
        this.requsetRoot = requsetRoot;
        this.taehaedVModel = taehaedVModel;

    }

    @NonNull
    @Override
    public OparetaionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_servies, parent, false);

        return new OperationAdapter.OparetaionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OparetaionHolder holder, int position) {
        holder.BindtheData(requsetRoot.request.getRequest_services().get(position));
    }

    @Override
    public int getItemCount() {
        return requsetRoot.request.getRequest_services().size();
    }

    class OparetaionHolder extends RecyclerView.ViewHolder {
        private RequestServiesBinding binding;
        private Activity context;

        public OparetaionHolder(@NonNull View itemView) {
            super(itemView);
            binding = RequestServiesBinding.bind(itemView);
            alertDialog = setAlertMeaage("جار اظهار البيانات", binding.getRoot().getContext());
            context = (Activity) binding.getRoot().getContext();

        }

        public void BindtheData(RequestService requestRequestService) {
            //اسم الخدمة
            binding.ServeText.setText(requestRequestService.getService());
            //رقم الخدمة
            binding.RequestNumber.setText("رقم الخدمة : " + requestRequestService.getId());

            //هنا يتم عملية اظهار الخدمات المتكلمة او الغير المتكملة وعملية اظهار الملاحظات
            ShowTheNotesAndDoneStatus(requestRequestService);
            // هنا بداية السايكل او رحلة المندوب
            binding.ListOfOperation.setOnClickListener(view -> {
                // في حالة لو الطلب ب 2 معني كده ان الطلب ده مبعوت لمندوب ومهمته في اللحظة دي انه يوافق او يرفض
                if (requestRequestService.getStatus() == 2) {
                    AcseptiServies(requestRequestService);

                }
                //في حالة لو الطلب ب 3 معني كده ان المندوب وافق علي الطلب ومهمته في اللحظة دي انه يملي الفورم ويضيف ملاحظات او يرجع في كلامه ويرجع الطلب لحالة رقم 2
                else if (requestRequestService.getStatus() == 3) {

                    FillServiesAndAddNotes(requestRequestService);

                }
                // اذا هي مش ب2  او 3 يبقا هي كده ب6 او خلصت  في الحالة دي بيقا الفوم اتملي بالبيانات ولايمكن التعديل عليه لكن يمكن حذف الفورم ده وارجاعه الي حالة رقم 3
                else {

                    alertDialog.show();
                    context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    taehaedVModel.getOperationReuqest(requestRequestService.getId(), Status -> {

                        if (Status) {
                            alertDialog.dismiss();
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            //  Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, OperartionQuset.class);
                            intent.putExtra(Constans.RequsetIDSend, requestRequestService.getId());
                            intent.putExtra(Constans.FormNumberSend, requestRequestService.getForm());
                            intent.putExtra(Constans.KayMoudleOpration, taehaedVModel.operationRequestRootMutableLiveData.getValue().request_service.form);
                            context.startActivity(intent);

                        } else {
                            alertDialog.dismiss();
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, "عفوا يبدو ان هناك خطأ ما", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            });

        }

        private void ShowTheNotesAndDoneStatus(RequestService requestRequestService) {
            if (requestRequestService.getDone() == 0) {
                binding.ChackPoint0.setImageResource(R.drawable.ic_notdone);

            }
            if (requestRequestService.getDone() == 1) {
                binding.ChackPoint0.setImageResource(R.drawable.ic_done);
            }

            if (requestRequestService.getStatus() == 2) {

                binding.StatuseOFServies.setText("( في الانتظار )");

                binding.StatuseOFServies.setTextColor(context.getResources().getColor(R.color.yello));
            } else if (requestRequestService.getStatus() == 3) {
                binding.StatuseOFServies.setText("( مقبول  )");

                binding.StatuseOFServies.setTextColor(context.getResources().getColor(R.color.black));

            } else if (requestRequestService.getStatus() == 4) {
                binding.StatuseOFServies.setText("( مكتمل )");


            }

        }

        private void FillServiesAndAddNotes(RequestService requestRequestService) {
            NotesAddsFragment notesAddsFragment = NotesAddsFragment.newInstance(requestRequestService.getId(), requestRequestService.getForm());
            FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
            notesAddsFragment.show(fragmentManager, "");
            notesAddsFragment.setCancelable(true);
        }

        private void AcseptiServies(RequestService requestRequestService) {
            ServiersFragment serviersFragment = ServiersFragment.newInstance(requestRequestService.getId(), requestRequestService.getService());
            FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
            serviersFragment.show(fragmentManager, "");
            serviersFragment.setCancelable(true);
        }
    }
}
