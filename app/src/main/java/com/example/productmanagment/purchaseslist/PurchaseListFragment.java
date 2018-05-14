package com.example.productmanagment.purchaseslist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.adapters.AccountSpinnerAdapter;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;
import com.github.clans.fab.FloatingActionButton;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PurchaseListFragment extends Fragment implements PurchaseListContract.View {
    PurchaseListContract.Presenter presenter;
    PurchaseListSpinnerAdapter adapter;
    PurchasesAdapter purchasesAdapter;
    AccountDialogSpinnerAdapter accountSpinnerAdapter;
    Spinner spinner;
    EditText newPurchaseEditText;
    Button selectAllButton, deselectAllButton, deletePurchaseListButton;
    FloatingActionButton addPurchaseListButton, renamePurchaseListButton;

    public PurchaseListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PurchaseListFragment newInstance() {
        return new PurchaseListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Покупки");
        adapter = new PurchaseListSpinnerAdapter(getContext(), android.R.layout.simple_spinner_item,
                new ArrayList<>(0), android.R.layout.simple_spinner_dropdown_item);
        purchasesAdapter = new PurchasesAdapter(new ArrayList<>(0), itemListener,
                getContext().getResources());
        accountSpinnerAdapter = new AccountDialogSpinnerAdapter(getContext(), R.layout.list_item_account_spinner,
                new ArrayList<>(0), R.layout.list_item_account_spinner);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.wtf("MyLog", "OnCreateView");
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_purchase_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.purchasesRecyclerView);
        newPurchaseEditText = view.findViewById(R.id.enterPurchaseEditText);
        newPurchaseEditText.setOnEditorActionListener(onEditorActionListener);
        selectAllButton = view.findViewById(R.id.selectAllPurchasesButton);
        selectAllButton.setOnClickListener(__ -> presenter.selectPurchases());
        deselectAllButton = view.findViewById(R.id.deselectAllPurchasesButton);
        deselectAllButton.setOnClickListener(__ -> presenter.removePurchasesSelection());
        deletePurchaseListButton = view.findViewById(R.id.deleteListButton);
        deletePurchaseListButton.setOnClickListener(__ -> presenter.deletePurchasesList(
                ((PurchaseList)spinner.getSelectedItem()).getId()));
        addPurchaseListButton = view.findViewById(R.id.add_purchaseList_fab_menu_item);
        addPurchaseListButton.setOnClickListener(__ -> presenter.openAddPurchaseListDialog());
        renamePurchaseListButton = view.findViewById(R.id.rename_purchaseList_fab_menu_item);
        renamePurchaseListButton.setOnClickListener(__ -> presenter.openRenamePurchaseListDialog());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(purchasesAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.wtf("MyLog", "onCreateOptionsMenu");
        inflater.inflate(R.menu.purchase_list_menu, menu);
        MenuItem item = menu.findItem(R.id.purchaseListSpinner);
        spinner = (Spinner) item.getActionView();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PurchaseList purchaseList = adapter.getItem(i);
                int purchaseListId = purchaseList.getId();
                presenter.loadPurchasesById(purchaseListId);
                purchasesAdapter.setPurchaseListTitle(purchaseList.getTitle());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share_purchase:
                presenter.sendPurchasesList(purchasesAdapter.getPurchasesShareText());
                break;
            case R.id.action_add_record:
                presenter.openPurchaseRecordDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void setPresenter(PurchaseListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPurchasesList(List<PurchaseList> purchaseList) {
        adapter.setData(purchaseList);
    }

    @Override
    public void showPurchases(List<Purchase> purchases) {
        purchasesAdapter.setData(purchases);
    }

    @Override
    public void showSendDialog(String text) {
        sendIntent(text);
    }

    @Override
    public void showAllPurchasesSelection() {
        purchasesAdapter.selectAll();
    }

    @Override
    public void showAllPurchasesDeselection() {
        purchasesAdapter.deselectAll();
    }

    @Override
    public void showAddExpenseRecord() {
        purchaseRecordDialog().show();
    }

    @Override
    public void showAddPurchaseList() {
        createPurchaseList().show();
    }

    @Override
    public void showRenamePurchaseList() {
        renamePurchaseList().show();
    }

    @Override
    public void showAccountList(List<Account> accounts) {
        accountSpinnerAdapter.setData(accounts);
    }

    private Dialog purchaseRecordDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_purchase_record, null);
        Spinner accountSpinner = view.findViewById(R.id.purchaseRecordAccountSpinner);
        accountSpinner.setAdapter(accountSpinnerAdapter);
        EditText recordEditText = view.findViewById(R.id.purchaseRecordTotalSumEditText);
        builder.setMessage("Створити запис")
                .setView(view)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", new Locale("ru"));
                        Date current = new Date();
                        String date = dateFormat.format(current);
                        String time = dateFormat.format(Calendar.getInstance().getTime());
                        String note = purchasesAdapter.getPurchasesRecordNote();
                        double amount = Double.valueOf(recordEditText.getText().toString());
                        Account account = (Account) accountSpinner.getSelectedItem();
                        Expense expense = new Expense(amount, "Витрата", note, "", date,
                                time, "1", "", "",
                                "", new Category(83), account, null);
                        expense.setExpenseType("Витрата");
                        presenter.createExpenseRecord(expense);
                    }
                })
                .setNegativeButton("Відміна", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

    private Dialog createPurchaseList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_purchase_list_title, null);
        EditText purchaseListTitleEditText = view.findViewById(R.id.dialogPurchaseListTitleEditText);
        builder.setMessage("Створити список покупок")
                .setView(view)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String purchaseListTitle = purchaseListTitleEditText.getText().toString();
                        if(!purchaseListTitle.isEmpty())
                            presenter.createPurchaseList(purchaseListTitle);
                    }
                })
                .setNegativeButton("Відміна", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

    private Dialog renamePurchaseList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        PurchaseList purchaseList = (PurchaseList) spinner.getSelectedItem();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_purchase_list_title, null);
        EditText purchaseListTitleEditText = view.findViewById(R.id.dialogPurchaseListTitleEditText);
        purchaseListTitleEditText.setText(purchaseList.getTitle());
        builder.setMessage("Переіменувати список покупок")
                .setView(view)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String purchaseListTitle = purchaseListTitleEditText.getText().toString();
                        if(!purchaseListTitle.isEmpty())
                            presenter.renamePurchaseList(purchaseList.getId(), purchaseListTitle);
                    }
                })
                .setNegativeButton("Відміна", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            boolean handled = false;
            if (i == EditorInfo.IME_ACTION_DONE) {
                String purchaseTitle = textView.getText().toString();
                int purchaseListId = ((PurchaseList)spinner.getSelectedItem()).getId();
                if(!purchaseTitle.equals("")) {
                    presenter.createPurchase(new Purchase(purchaseTitle, purchaseListId));
                    textView.setText("");
                }
                handled = true;
            }
            return handled;
        }
    };

    private void sendIntent(String text){
        if(!text.equals("")) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        else{
            Toast.makeText(getContext(), "Покупки не обрані", Toast.LENGTH_LONG).show();
        }
    }

    PurchaseItemListener itemListener = new PurchaseItemListener() {
        @Override
        public void onPurchaseClick(Purchase clicked) {

        }

        @Override
        public void deletePurchase(int id) {
            presenter.deletePurchase(id);
        }

        @Override
        public void checkPurchase(Purchase purchase) {
            purchase.setChecked(!purchase.isChecked());
            Toast.makeText(getContext(), purchase.getTitle() + " " + purchase.isChecked(), Toast.LENGTH_LONG).show();
        }
    };

    private class PurchaseListSpinnerAdapter extends ArrayAdapter<PurchaseList> {
        private LayoutInflater mInflater;
        private Context context;
        private List<PurchaseList> items;
        private int resource, itemResource;

        public PurchaseListSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<PurchaseList> objects, int itemResource) {
            super(context, resource, objects);
            this.context = context;
            mInflater = LayoutInflater.from(context);
            this.items = objects;
            this.resource = resource;
            this.itemResource = itemResource;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(itemResource, position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(resource, position, convertView, parent);
        }

        private View createItemView(int resource, int position, View convertView, ViewGroup parent) {
            final View view = mInflater.inflate(resource, parent, false);
            if (resource == this.itemResource) {
                view.setBackgroundColor(Color.parseColor("#eeeeee"));
            }

            PurchaseList purchaseList = getItem(position);
            TextView purchaseListTextView = view.findViewById(android.R.id.text1);
            if (resource == this.resource)
                purchaseListTextView.setTextColor(Color.WHITE);
            purchaseListTextView.setText(purchaseList.getTitle());
            return view;
        }

        @Nullable
        @Override
        public PurchaseList getItem(int position) {
            return items.get(position);
        }

        public void setData(List<PurchaseList> accounts) {
            this.items.clear();
            this.items.addAll(accounts);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    private class AccountDialogSpinnerAdapter extends ArrayAdapter<Account> {
        private LayoutInflater mInflater;
        private Context context;
        private List<Account> items;
        private int resource, itemResource;

        public AccountDialogSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Account> objects, int itemResource) {
            super(context, resource, objects);
            this.context = context;
            mInflater = LayoutInflater.from(context);
            this.items = objects;
            this.resource = resource;
            this.itemResource = itemResource;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(itemResource, position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(resource, position, convertView, parent);
        }

        private View createItemView(int resource, int position, View convertView, ViewGroup parent) {
            final View view = mInflater.inflate(resource, parent, false);

            Account account = getItem(position);
            TextView accountTextView = view.findViewById(R.id.accountNameTextView);
            accountTextView.setText(account.getName());
            return view;
        }

        @Nullable
        @Override
        public Account getItem(int position) {
            return items.get(position);
        }

        public void setData(List<Account> accounts) {
            this.items.clear();
            this.items.addAll(accounts);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    private static class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder>{
        private List<Purchase> purchaseList;
        private PurchaseItemListener itemListener;
        private Resources resources;
        private String purchaseListTitle;

        public PurchasesAdapter(List<Purchase> purchaseList, PurchaseItemListener itemListener, Resources resources) {
            this.purchaseList = purchaseList;
            this.itemListener = itemListener;
            this.resources = resources;
        }

        @Override
        public PurchasesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_purchase, parent, false);
            return new PurchasesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PurchasesAdapter.ViewHolder holder, int position) {
            Purchase purchase = purchaseList.get(position);
            holder.bind(purchase);
        }

        @Override
        public int getItemCount() {
            return purchaseList.size();
        }

        public void setData(List<Purchase> purchaseList){
            this.purchaseList = purchaseList;
            notifyDataSetChanged();
        }

        public void selectAll(){
            for(Purchase purchase : purchaseList){
                purchase.setChecked(true);
            }
            notifyDataSetChanged();
        }

        public void deselectAll(){
            for(Purchase purchase : purchaseList){
                purchase.setChecked(false);
            }
            notifyDataSetChanged();
        }

        public String getPurchasesShareText(){
            String text = resources.getString(R.string.share_title_list);
            for(Purchase purchase : purchaseList){
                if(purchase.isChecked()){
                    String purchaseText = resources.getString(R.string.share_purchases_item_string,
                            purchase.getTitle());
                    text = text.concat(purchaseText);
                }
            }
            return text;
        }

        public String getPurchasesRecordNote(){
            String text = "";
            for(Purchase purchase : purchaseList){
                if(purchase.isChecked())
                    text = text.concat(resources.getString(R.string.purchase_note_item, purchase.getTitle()));
            }
            return text;
        }

        public void setPurchaseListTitle(String purchaseListTitle) {
            this.purchaseListTitle = purchaseListTitle;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Purchase purchase;
            TextView purchaseTitleTextView;
            ImageButton deletePurchaseImageButton;
            CheckBox selectPurchaseCheckBox;

            public ViewHolder(View view) {
                super(view);
                purchaseTitleTextView = view.findViewById(R.id.purchaseTextView);
                selectPurchaseCheckBox = view.findViewById(R.id.purchaseCheckBox);
                deletePurchaseImageButton = view.findViewById(R.id.purchaseDeleteButton);

                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onPurchaseClick(purchase));

                deletePurchaseImageButton.setOnClickListener(__ -> itemListener.deletePurchase(purchase.getId()));
                selectPurchaseCheckBox.setOnClickListener(__ -> itemListener.checkPurchase(purchase));
            }

            public void bind(Purchase purchase){
                this.purchase = purchase;
                purchaseTitleTextView.setText(purchase.getTitle());
                selectPurchaseCheckBox.setChecked(purchase.isChecked());
            }
        }
    }

    interface PurchaseItemListener{
        void onPurchaseClick(Purchase clicked);
        void deletePurchase(int id);
        void checkPurchase(Purchase purchase);
    }
}
