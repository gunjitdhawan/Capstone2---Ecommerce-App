package in.grappes.ecommerce.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by GunjitDhawan on 11/13/2015.
 */
public class HindiEditText extends EditText {

    public HindiEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HindiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HindiEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ALLERRG.ttf");
            setTypeface(tf);
        }
    }

}