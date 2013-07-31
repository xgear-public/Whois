package by.xgear.whois.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import by.xgear.whois.R;
import by.xgear.whois.entity.Image;
import by.xgear.whois.entity.Person;

public class TasteAdapter extends BaseAdapter {
	
	List<Person> mData;
	
	public TasteAdapter(List<Person> mData) {
		super();
		this.mData = mData;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Person getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, null, false);
			holder = new ViewHolder();
			holder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
			holder.icon = (ImageView) convertView.findViewById(R.id.user_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Person person = mData.get(position);
		ImageLoader.getInstance().displayImage(getBestUrl(person.getImages()), holder.icon);
		holder.artistName.setText(person.getName());
		return convertView;
	}
	
	static class ViewHolder{
		ImageView icon;
		TextView artistName;
	}

	public static String getBestUrl(List<Image> images) {
		String url = null;
		if(images != null && !images.isEmpty()) {
			url = images.get(0).getUrl();
			int priority = 0;
			for(Image img : images) {
				int tmpPriority = Image.ImageSize.getPriorityBySize(img.getSize());
				if(tmpPriority > priority){
					priority = tmpPriority;
					url = img.getUrl();
				}
			}
		}
		return url;
	}
	
	public void setData(List<Person> data) {
		mData = data;
	}

}
