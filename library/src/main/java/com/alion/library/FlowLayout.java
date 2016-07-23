package com.alion.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  AGoodLayout 
 *  @包名：    com.alion.library
 *  @文件名:   FlowLayout
 *  @创建者:   Alion
 *  @创建时间:  2016/7/22 21:59
 *  @描述：    这是一个很棒的流线型的布局
 */
public class FlowLayout
        extends ViewGroup
{
    private static final String     TAG              = "FlowLayout";
    private              List<Line> mLines           = new ArrayList<>();
    private              int        mVerticalSpace   = 15;//Vertical space height
    private              int        mHorizontalSpace = 15;//Vertical space height
    private Line mCurrentLine;   //of name

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setSpace(int horizontal, int vertical) {
        mVerticalSpace = vertical;
        mHorizontalSpace = horizontal;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //clear line
        mLines.clear();
        mCurrentLine = null;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        //get line maxLineSize
        int chileWidthMax = width - getPaddingLeft() - getPaddingRight();
        //1.measure child
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                //if gone    the continue
                continue;
            }
            //measure child    this method need self-adaption size
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // add in line
            if (mCurrentLine == null) {
                //not have a line
                mCurrentLine = new Line(chileWidthMax, mHorizontalSpace);
                //add line
                mLines.add(mCurrentLine);
                //add child
                mCurrentLine.addChild(child);
            } else {
                //judge is add child
                if (mCurrentLine.canAdd(child)) {
                    //of course
                    mCurrentLine.addChild(child);
                } else {
                    //don't add childView
                    mCurrentLine = new Line(chileWidthMax, mHorizontalSpace);
                    //add line
                    mLines.add(mCurrentLine);
                    //add child
                    mCurrentLine.addChild(child);

                }
            }

        }
        // 2. measure for me
        int height = getPaddingBottom() + getPaddingTop();//line height
        //get everyone line of height
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            height += line.mLineHeight;

            if (i != mLines.size() - 1) {
                height += mVerticalSpace;
            }

        }
        // measure for mes
        setMeasuredDimension(width, height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //We need lyaout mLines
        int top = getPaddingTop();
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);


            line.layout(getPaddingLeft(), top);

            top += mVerticalSpace + line.mLineHeight;
        }
    }


    /**
     * save line of view and attrs
     *
     * the method canAdd() and addChild()  Can merge
     */
    private class Line {
        //1.attribute 2.meathod 3.construction
        //save child view container
        private List<View> mViews = new ArrayList<>();

        private int mLineMaxWidth;     //line width
        private int mLineHeight;    //line height
        private int mSpace;         //space width
        private int mLineUsedWidth;// usesr width

        //setting maxWidth and mspace
        public Line(int maxWidth, int space) {
            mLineMaxWidth = maxWidth;
            mSpace = space;
        }


        /**
         * Can judge add child
         * @param view
         * @return
         */
        public boolean canAdd(View view) {
            if (mViews.size() == 0) {
                //not have child
                return true;
            }

            int childWidth = view.getMeasuredWidth();//child of width

            if ((mLineUsedWidth + mSpace + childWidth) <= mLineMaxWidth) {
                //could add
                return true;
            }
            return false;
        }

        /**
         * add child view method
         * Before calling the method of calling canAdd method
         * @param view
         */
        public void addChild(View view) {

            int childWidth  = view.getMeasuredWidth();//child of width
            int childHeight = view.getMeasuredHeight();


            if (mViews.size() == 0) {
                //not have child view
                mLineUsedWidth = childWidth;
                //calc line height
                mLineHeight = childHeight;
            } else {
                //calc width
                mLineUsedWidth += childWidth + mSpace;
                //cacl line height for more child
                mLineHeight = mLineHeight < childHeight
                              ? childHeight
                              : mLineHeight;
            }
            //save child
            mViews.add(view);

        }

        //The line lauout method
        public void layout(int left, int top) {
            //Can judge have space
            int extraWidth = mLineMaxWidth - mLineUsedWidth;
            //hava everyone view avg
            int avgWidth = (int) (extraWidth * 1f / mViews.size() + 0.5f);


            for (int i = 0; i < mViews.size(); i++) {

                View child      = mViews.get(i);
                int  childWidth = child.getMeasuredWidth();
                int  childHight = child.getMeasuredHeight();

                //The first not layout,because best have expect children width and height

                if (avgWidth > 0) {
                    int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth+ avgWidth,MeasureSpec.EXACTLY);

                    int childHightSpec = MeasureSpec.makeMeasureSpec(childHight,
                                                                     MeasureSpec.EXACTLY);

                    child.measure(childWidthSpec,childHightSpec);

                    //the new width and hight
                    childWidth = child.getMeasuredWidth();
                    childHight = child.getMeasuredHeight();
                }



                int l = left;
                int r = l + childWidth;
//                int t = top;
                int t = (int) (top + (mLineHeight - childHight)/2f);
                int b = t + childHight;
                child.layout(l, t, r, b);

                //notes leftWidth of next
                left += mSpace + childWidth;

            }

        }
    }


}
