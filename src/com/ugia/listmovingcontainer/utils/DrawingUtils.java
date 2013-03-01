/*
 * Copyright 2013 Jose L Ugia - @jl_ugia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ugia.listmovingcontainer.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawingUtils {

    
    /**
     * This methods intends to work as a replacement of the method setAlpha applied to views
     * after ICS, by exploring the whole tree of views and setting the alpha to the elements
     * in the last part of the hierachy
     *
     * @param  alpha  alpha value among 0-255 to set to the tree of views
     * @param  view view to which the alpha application is desired (with all its descendants)
     * @return      true if the operation run successfully
     */
    public static boolean onSetAlpha(int alpha, View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                onSetAlpha(alpha, ((ViewGroup) view).getChildAt(i));
                if (((ViewGroup) view).getBackground() != null) {
                    ((ViewGroup) view).getBackground().setAlpha(alpha);
                }
            }
        } else if (view instanceof ImageView) {
            if (((ImageView) view).getDrawable() != null) {
                ((ImageView) view).getDrawable().setAlpha(alpha);
            }
            if (((ImageView) view).getBackground() != null) {
                ((ImageView) view).getBackground().setAlpha(alpha);
            }
        } else if (view instanceof TextView) {
            ((TextView) view).setTextColor(((TextView) view).getTextColors().withAlpha(alpha));
            if (((TextView) view).getBackground() != null) {
                ((TextView) view).getBackground().setAlpha(alpha);
            }
        } else if (view instanceof EditText) {
            ((EditText) view).setTextColor(((EditText) view).getTextColors().withAlpha(alpha));
            if (((EditText) view).getBackground() != null) {
                ((EditText) view).getBackground().setAlpha(alpha);
            }
        }
        return true;
    }

}
