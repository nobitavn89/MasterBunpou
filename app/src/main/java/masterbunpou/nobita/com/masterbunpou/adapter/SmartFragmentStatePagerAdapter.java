package masterbunpou.nobita.com.masterbunpou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by nobitavn89 on 15/11/06.
 * smart Fragment that intelligently caches the Fragments in the ViewPager
 * from: https://gist.github.com/nesquena/c715c9b22fb873b1d259
 */
public abstract class SmartFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private SparseArray<T> registeredFragments = new SparseArray<>();

    public SmartFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T fragment = (T) super.instantiateItem(container, position);
        registeredFragments.put(position,fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public T getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
