package com.tm.wrappers;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import com.tm.entities.Chat;

public class SetChats extends AbstractList<Chat> {

	private List<Chat> list;

	public List<Chat> getList() {
		return list;
	}

	public SetChats(List<Chat> list) {
		this.list = list;
	}

	public SetChats(Chat c) {
		this.list = Arrays.asList(c);
	}

	/**
	 * return the latest used Chat
	 * 
	 * @return
	 */
	public Chat latest() {
		if (list.isEmpty()) {
			return null;
		}
		Chat res = null;
		for (Chat c : list) {
			if (c == null) {
				continue;
			}
			if (res == null) {
				res = c;
				continue;
			}
			if (c.getLatestMessage().getDate().isAfter(res.getLatestMessage().getDate())) {
				res = c;
			}
		}
		return res;
	}

	@Override
	public Chat get(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

}
