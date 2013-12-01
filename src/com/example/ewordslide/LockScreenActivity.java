package com.example.ewordslide;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class LockScreenActivity extends Activity {

	//text views being dragged and dropped onto
	private TextView option1, choice1, choice2;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock_screen);
	
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED 
		| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		
		
		option1 = (TextView)findViewById(R.id.option_1);
		choice1 = (TextView)findViewById(R.id.choice_1);
		choice2 = (TextView)findViewById(R.id.choice_2);
		option1.setOnTouchListener(new ChoiceTouchListener());
		choice1.setOnDragListener(new ChoiceDragListener());
		choice2.setOnDragListener(new ChoiceDragListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lock_screen, menu);
		return true;
	}


	/**
	 * ChoiceTouchListener will handle touch events on draggable views
	 *
	 */
	private final class ChoiceTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				/*
				 * Drag details: we only need default behavior
				 * - clip data could be set to pass data as part of drag
				 * - shadow can be tailored
				 */
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				//start dragging the item touched
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			} else {
				return false;
			}
		}
	} 

	/**
	 * DragListener will handle dragged views being dropped on the drop area
	 * - only the drop action will have processing added to it as we are not
	 * - amending the default behavior for other parts of the drag process
	 *
	 */
	private class ChoiceDragListener implements OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				//no action necessary
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				//no action necessary
				break;
			case DragEvent.ACTION_DRAG_EXITED:        
				//no action necessary
				break;
			case DragEvent.ACTION_DROP:
				//handle the dragged view being dropped over a drop view
				View view = (View) event.getLocalState();
				//stop displaying the view where it was before it was dragged
				view.setVisibility(View.INVISIBLE);
				//view dragged item is being dropped on
				TextView dropTarget = (TextView) v;
				//view being dragged and dropped
				TextView dropped = (TextView) view;
				//update the text in the target view to reflect the data being dropped
				dropTarget.setText(dropped.getText());
				//make it bold to highlight the fact that an item has been dropped
				dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
				//if an item has already been dropped here, there will be a tag
				Object tag = dropTarget.getTag();
				//if there is already an item here, set it back visible in its original place
				if(tag!=null)
				{
					//the tag is the view id already dropped here
					int existingID = (Integer)tag;
					//set the original view visible again
					findViewById(existingID).setVisibility(View.VISIBLE);
				}
				//set the tag in the target view being dropped on - to the ID of the view being dropped
				dropTarget.setTag(dropped.getId());
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				//no action necessary
				break;
			default:
				break;
			}
			return true;
		}
	} 
}
