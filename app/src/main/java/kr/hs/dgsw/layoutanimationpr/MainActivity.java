package kr.hs.dgsw.layoutanimationpr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener {

    private Scene mScene1;
    private Scene mScene2;
    private Scene mScene3;

    /** A custom TransitionManager */
    private TransitionManager mTransitionManagerForScene3;

    /** Transitions take place in this ViewGroup. We retain this for the dynamic transition on scene 4. */
    private ViewGroup mSceneRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.select_scene);
        radioGroup.setOnCheckedChangeListener(this);
        mSceneRoot = (ViewGroup) findViewById(R.id.scene_root);

        // BEGIN_INCLUDE(instantiation_from_view)
        // A Scene can be instantiated from a live view hierarchy.
        mScene1 = new Scene(mSceneRoot, (ViewGroup) mSceneRoot.findViewById(R.id.container));
        // END_INCLUDE(instantiation_from_view)

        // BEGIN_INCLUDE(instantiation_from_resource)
        // You can also inflate a generate a Scene from a layout resource file.
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene2, this);
        // END_INCLUDE(instantiation_from_resource)

        // Another scene from a layout resource file.
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene3, this);

        // BEGIN_INCLUDE(custom_transition_manager)
        // We create a custom TransitionManager for Scene 3, in which ChangeBounds and Fade
        // take place at the same time.
        mTransitionManagerForScene3 = TransitionInflater.from(this)
                .inflateTransitionManager(R.transition.scene3_transition_manager, mSceneRoot);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.select_scene_1: {
                // BEGIN_INCLUDE(transition_simple)
                // You can start an automatic transition with TransitionManager.go().
                TransitionManager.go(mScene1);
                // END_INCLUDE(transition_simple)
                break;
            }
            case R.id.select_scene_2: {
                TransitionManager.go(mScene2);
                break;
            }
            case R.id.select_scene_3: {
                // BEGIN_INCLUDE(transition_custom)
                // You can also start a transition with a custom TransitionManager.
                mTransitionManagerForScene3.transitionTo(mScene3);
                // END_INCLUDE(transition_custom)
                break;
            }
            case R.id.select_scene_4: {
                // BEGIN_INCLUDE(transition_dynamic)
                // Alternatively, transition can be invoked dynamically without a Scene.
                // For this, we first call TransitionManager.beginDelayedTransition().
                TransitionManager.beginDelayedTransition(mSceneRoot);
                // Then, we can just change view properties as usual.
                View square = mSceneRoot.findViewById(R.id.transition_square);
                ViewGroup.LayoutParams params = square.getLayoutParams();
                int newSize = getResources().getDimensionPixelSize(R.dimen.square_size_expanded);
                params.width = newSize;
                params.height = newSize;
                square.setLayoutParams(params);
                // END_INCLUDE(transition_dynamic)
                break;
            }
        }
    }
}
