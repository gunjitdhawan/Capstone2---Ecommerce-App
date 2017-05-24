package in.grappes.ecommerce.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by GunjitDhawan on 11/13/2015.
 */
public class HindiFont extends TextView {

    public HindiFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HindiFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HindiFont(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ALLERRG.ttf");
        setTypeface(tf ,1);

    }

}