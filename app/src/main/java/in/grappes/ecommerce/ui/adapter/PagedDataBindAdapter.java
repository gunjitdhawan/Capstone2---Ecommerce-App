package in.grappes.ecommerce.ui.adapter;

public abstract class PagedDataBindAdapter extends DataBindAdapter {

//    private boolean isHeader;
//    public boolean noItemsLeft;
//    public boolean isLoading;
//    private ViewHolder footerViewHolder;
//    public List arrayList;
//    int headerItemCount = 0;
//    public Context context;
//    public Fragment fragment;
//
//    public PagedDataBindAdapter(Context context, List arrayList, int headerItemCount,
//                                FooterClickListener footerClickListener) {
//        this.arrayList = arrayList;
//        this.headerItemCount = headerItemCount;
//        this.context = context;
//        this.footerClickListener = footerClickListener;
//    }
//
//    public PagedDataBindAdapter(Context context, List arrayList, int headerItemCount,
//                                FooterClickListener footerClickListener, boolean isHeader) {
//        this.arrayList = arrayList;
//        this.headerItemCount = headerItemCount;
//        this.footerClickListener = footerClickListener;
//        this.context = context;
//        this.isHeader = isHeader;
//    }
//
//    public ViewHolder getViewHolder(ViewGroup parent) {
//        if (footerViewHolder == null) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.recylerview_refresh_footer, parent, false);
//
//            footerViewHolder = new ViewHolder(view);
//        }
//        return footerViewHolder;
//    }
//
//    int footerShowArraySize = 5;
//
//    public void setFooterShowArraySize(int footerShowArraySize) {
//        this.footerShowArraySize = footerShowArraySize;
//        if (footerViewHolder != null) {
//            if (arrayList.size() < footerShowArraySize || noItemsLeft) {
//                footerViewHolder.loadMoreTxt.setVisibility(View.GONE);
//                footerViewHolder.footerLoadingBar.setVisibility(View.GONE);
//                return;
//            }
//            if (isLoading) {
//                footerViewHolder.loadMoreTxt.setVisibility(View.INVISIBLE);
//                footerViewHolder.footerLoadingBar.setVisibility(View.VISIBLE);
//            } else {
//                footerViewHolder.loadMoreTxt.setVisibility(View.VISIBLE);
//                footerViewHolder.footerLoadingBar.setVisibility(View.INVISIBLE);
//            }
//        }
//    }
//
//    String loadMoreTxt = "Load More";
//    private boolean isComment;
//
//    public void setLoadMoreTxt(String text) {
//        loadMoreTxt = text;
//        if(loadMoreTxt.equalsIgnoreCase("Load previous comments")){
//            isComment=true;
//        }
//        if (footerViewHolder != null) {
//            footerViewHolder.loadMoreTxt.setText(loadMoreTxt);
//        }
//    }
//
//    public void bindViewHolder(final ViewHolder holder, int position) {
//
//        if(isComment){
//            if(arrayList.size()+1>headerItemCount){
//                holder.loadMoreTxt.setText(loadMoreTxt);
//            }else {
//                holder.loadMoreTxt.setText("Load comments");
//            }
//        }
//
//        if ((!isHeader && position == 0) || (footerShowArraySize == 5 && arrayList.size() < footerShowArraySize) || noItemsLeft) {
//            holder.loadMoreTxt.setVisibility(View.GONE);
//            holder.footerLoadingBar.setVisibility(View.GONE);
//            return;
//        }
//        if (isLoading) {
//            holder.loadMoreTxt.setVisibility(View.INVISIBLE);
//            holder.footerLoadingBar.setVisibility(View.VISIBLE);
//        } else {
//            holder.loadMoreTxt.setVisibility(View.VISIBLE);
//            holder.footerLoadingBar.setVisibility(View.INVISIBLE);
//        }
//        holder.footerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (footerClickListener != null) {
//
//                    if (footerClickListener.footerClicked()) {
//
//                        onViewRefreshStarted();
//                    }
//                }
//            }
//        });
//
//    }
//
//    public void onViewRefreshStarted() {
//
//        if (footerViewHolder != null) {
//
//            if (arrayList.size() <= headerItemCount) {
//
//                footerViewHolder.loadMoreTxt.setVisibility(View.INVISIBLE);
//                footerViewHolder.footerLoadingBar.setVisibility(View.INVISIBLE);
//                return;
//            }
//
//            footerViewHolder.loadMoreTxt.setVisibility(View.INVISIBLE);
//            footerViewHolder.footerLoadingBar.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void onViewRefreshEnded() {
//
//        if (footerViewHolder != null) {
//
//            if (!AppUtils.isConnected(context)) {
//                noItemsLeft = false;
//
//            }
//
//            if (noItemsLeft) {
//                footerViewHolder.loadMoreTxt.setText("");
//                footerViewHolder.loadMoreTxt.setVisibility(View.GONE);
//                footerViewHolder.footerLoadingBar.setVisibility(View.GONE);
//
//            } else {
//
//                if ((!AppUtils.isConnected(context) && arrayList.size() == 0) || arrayList.size() > headerItemCount) {
//                    footerViewHolder.footerLoadingBar.setVisibility(View.INVISIBLE);
//                    footerViewHolder.loadMoreTxt.setVisibility(View.VISIBLE);
//
//                } else {
//                    footerViewHolder.loadMoreTxt.setVisibility(View.INVISIBLE);
//                    footerViewHolder.footerLoadingBar.setVisibility(View.INVISIBLE);
//
//                }
//            }
//        }
//    }
//
//
//    FooterClickListener footerClickListener;
//
//    public interface FooterClickListener {
//        boolean footerClicked();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public View footerView;
//        public View footerLoadingBar;
//        public TextView loadMoreTxt;
//
//        public ViewHolder(View view) {
//            super(view);
//            this.footerLoadingBar = view.findViewById(R.id.footerLoadingBar);
//            this.footerView = view.findViewById(R.id.footerView);
//            this.loadMoreTxt = (TextView) view.findViewById(R.id.loadMoreText);
//        }
//
//
//    }
}
