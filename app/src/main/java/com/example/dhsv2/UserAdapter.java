package com.example.dhsv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//the user adapter sets the data and  extends the recycler view
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {

    //creating a users list
    List<User> userList;
    Context context;
    //creating a list variable for searching using the city
    List<User> listFull;

    //creating a constructor of the class
      UserAdapter(Context context, List<User> users){
        this.context = context;
        userList = users;
        listFull = new ArrayList<>(userList);
    }


    @NonNull
    @Override
    //posting the context to the items.xml - around a View that contains the layout for an individual item in the list
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new UserViewHolder(view);
    }

    // logic to be sent content to the view using the bind view
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user= userList.get(position);
       // holder._id.setText("id : " + user.getId());
        holder.NAME.setText(user.getNAME());
        holder.SURNAME.setText(user.getSURNAME());
        holder.AGE.setText(""+user.getAGE());
        holder.CITY.setText(user.getCITY());

    }

    @Override
    public int getItemCount() {

        return userList.size();
    }

    //method returning the search results from the
    @Override
    public  Filter getFilter() {
        return FilterUser;
    }

    //filter user objects that gets the search text for the search view a, specifies the matching pattern
    //and returns the filter results that are set to the temporal list item
    private Filter FilterUser= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString();
            List<User>tempList = new ArrayList<>();
            if (searchText.length()==0 || searchText.isEmpty()){
                tempList.addAll(listFull);
            }
            else{
                for (User item: listFull){
                    if (item.getCITY().contains(searchText)){
                        tempList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }

        //this publishes the search results but then clears the original list and assigns it the filtered results values
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            userList.clear();
            userList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    //this class the extends the view holder of the recyslerview
    //also maps the data from the API variables to the TextViews on the items.xml
    public class UserViewHolder extends RecyclerView.ViewHolder{
        // getting items widgets using ID
        TextView _id, NAME,SURNAME, AGE,CITY;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            //_id = itemView.findViewById(R.id._id_tv);
            NAME = itemView.findViewById(R.id.name_tv);
            SURNAME = itemView.findViewById(R.id.surname_tv);
            AGE = itemView.findViewById(R.id.age_tv);
            CITY = itemView.findViewById(R.id.city_tv);
        }
    }
}
