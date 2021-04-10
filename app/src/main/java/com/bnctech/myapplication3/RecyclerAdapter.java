package com.bnctech.myapplication3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public RecyclerAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    //뷰id를 참조함
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.child_item,parent,false);  //카드뷰 : itemView
        //뷰 홀더(뷰를 참조하는 참조변수를 가지고 있음) 객체 생성 및 리턴
        VH holder = new VH(itemView);
        //그냥 일반 MyAdapter는 itemView.fid하는것

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder; //VH클래스 생성때 제네릭 하면 다운캐스팅 생략 할수 있다

        //현재번째(position)데이터를 가진 item객체 얻어오기
        Item item = items.get(position);

        //vh.civ.setImageResource( item.profileImg ); //item.profileImg 는 리소스(자원), vh.civ는 뷰객체(액자)=
        vh.beforePrice.setText( item.beforePrice );
        vh.afterPrice.setText( item.afterPrice );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //아이템뷰 안에 있는 뷰들의 참조변수를 멤버로 갖는 클래스
    //RecyclerView에서는 상속 받아야 한다.시스템 안에 있기 때문에
    class VH extends RecyclerView.ViewHolder{

        //생성자에서 fid참조한후 멤버변수에 잡아놓는다(Holder)=
        TextView beforePrice;
        TextView afterPrice;

        public VH(@NonNull View itemView) {
            super(itemView);
            beforePrice = itemView.findViewById(R.id.child_item_beforePrice);
            afterPrice = itemView.findViewById(R.id.child_item_afterPrice);

            //아이템클릭(카드뷰 자식뷰들) 리스너 생성 및 설정을 MyAdapter에서 설정한다, 복습할것
            //itemView.setOnClickListener, itemView한테 리스너 붙이면 cardview 안에있는 모든 뷰 클릭에 반응함
//            civ.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //현재 클릭한 번째(getLayoutPosition())의 데이터 참조
//                    Item item = items.get(getLayoutPosition());
//
//                    Intent intent = new Intent(context,DetailActivity.class);
//
//                    //전달할 데이터 추가
//                    intent.putExtra("Name",item.name);
//                    intent.putExtra("Img",item.profileImg);
//
//                    //전환 효과 (api 21버전 이상에서만 가능함)
//                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context, new Pair<View, String>(civ,"Img"));
//                        //civ에 붙이기, "Img"는 별명(식별자)
//                        context.startActivity(intent, options.toBundle());
//                    }else{
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }
    }
}