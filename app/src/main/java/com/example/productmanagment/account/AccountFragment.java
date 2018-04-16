package com.example.productmanagment.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.accountdetailandedit.AccountDetailAndEditActivity;
import com.example.productmanagment.addaccount.AddAccountActivity;
import com.example.productmanagment.data.models.Account;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements AccountContract.View {
    private AccountContract.Presenter presenter;
    private AccountsAdapter adapter;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AccountsAdapter(new ArrayList<Account>(0), listener);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        getActivity().setTitle("Рахунки");
        FloatingActionButton floatingActionButton = view.findViewById(R.id.addAccountButton);
        floatingActionButton.setOnClickListener(__ -> presenter.openAddAccount());
        RecyclerView recyclerView = view.findViewById(R.id.accountRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void showAccounts(List<Account> accountList) {
        adapter.setData(accountList);
    }

    @Override
    public void showAddAccount() {
        Intent intent = new Intent(getContext(), AddAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAccountDetailAndEdit(String accountId) {
        Intent intent = new Intent(getContext(), AccountDetailAndEditActivity.class);
        intent.putExtra("account_id", accountId);
        startActivity(intent);
    }

    AccountItemListener listener = new AccountItemListener() {
        @Override
        public void onAccountClick(Account clicked) {
            presenter.openAccountDetailAndEdit(clicked);
        }
    };

    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public static class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder>{
        private List<Account> accountList;
        AccountItemListener itemListener;

        public AccountsAdapter(List<Account> accountList, AccountItemListener itemListener) {
            this.accountList = accountList;
            this.itemListener = itemListener;
        }

        @Override
        public AccountsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_account, parent, false);
            return new AccountsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountsAdapter.ViewHolder holder, int position) {
            Account account = accountList.get(position);
            holder.bind(account);
        }

        @Override
        public int getItemCount() {
            return accountList.size();
        }

        public void setData(List<Account> accounts){
            this.accountList = accounts;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Account account;
            TextView accountTitleTextView;
            MaterialLetterIcon accountColorIcon;

            public ViewHolder(View view) {
                super(view);
                accountTitleTextView = view.findViewById(R.id.accountTitleTextView);
                accountColorIcon = view.findViewById(R.id.accountColorIcon);

                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onAccountClick(account));
            }

            public void bind(Account account){
                this.account = account;
                accountTitleTextView.setText(account.getName());
                if(account.getColor() != null)
                    accountColorIcon.setShapeColor(Color.parseColor(account.getColor()));
            }
        }
    }

    public interface AccountItemListener {

        void onAccountClick(Account clicked);

    }
}
