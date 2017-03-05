package com.maximomrtnz.moneybox.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximomrtnz.moneybox.R;
import com.maximomrtnz.moneybox.commons.DateUtils;
import com.maximomrtnz.moneybox.model.Movement;

import java.util.List;

/**
 * Created by Maxi on 2/23/2017.
 */

public class MovementRecyclerViewAdapter extends RecyclerView.Adapter<MovementRecyclerViewAdapter.MovementViewHolder> {

    private List<Movement> mMovements;
    private Context mContext;
    private static RecyclerViewClickListener mItemListener;

    public interface RecyclerViewClickListener {
        void onRecyclerViewListClicked(Movement movement);
    }

    public MovementRecyclerViewAdapter(Context context, List<Movement> movements,RecyclerViewClickListener itemListener) {
        mMovements = movements;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public MovementViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movement_list_item, viewGroup, false);

        MovementViewHolder movementViewHolder = new MovementViewHolder(v);

        return movementViewHolder;

    }

    @Override
    public void onBindViewHolder(MovementViewHolder holder, int position) {
        Movement movement = getItem(position);

        holder.mMovement = movement;
        holder.mMovementCategory.setText(movement.getCategory());
        holder.mMovementDescription.setText(movement.getDescription());
        holder.mMovementAmount.setText(movement.getAmount().toString());
        holder.mMovementDate.setText(DateUtils.calendarToString(DateUtils.getCalendarFromTimeInMillis(movement.getDate()),"dd/MM/yyyy"));

        if(movement.getType().toLowerCase().equals("income")){
            holder.mMovementType.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_add_green_24dp));
        }else{
            holder.mMovementType.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_remove_red_24dp));
        }

    }

    @Override
    public int getItemCount() {
        return mMovements.size();
    }

    public Movement getItem(int position){
        return  mMovements.get(position);
    }

    public static class MovementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mMovementCategory;
        TextView mMovementDescription;
        TextView mMovementAmount;
        TextView mMovementDate;
        ImageView mMovementType;
        Movement mMovement;

        public MovementViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mMovementDate = (TextView)itemView.findViewById(R.id.date);
            mMovementAmount = (TextView)itemView.findViewById(R.id.amount);
            mMovementCategory = (TextView)itemView.findViewById(R.id.category);
            mMovementDescription = (TextView) itemView.findViewById(R.id.description);
            mMovementType = (ImageView) itemView.findViewById(R.id.type);

        }

        @Override
        public void onClick(View view) {
            view.setTag(mMovement);
            if(mItemListener!=null){
                mItemListener.onRecyclerViewListClicked(mMovement);
            }
        }
    }

}
