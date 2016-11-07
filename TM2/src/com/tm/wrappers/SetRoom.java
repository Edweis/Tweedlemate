package com.tm.wrappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tm.entities.Chat;
import com.tm.entities.Room;

/**
 * Wrapper of a list of room
 * 
 * @author François Rullière
 *
 */
public class SetRoom implements Iterable<Room> {
	private List<Room> list;

	public SetRoom(List<Room> rooms) {
		if (rooms.contains(null)) {
			throw new RuntimeException("the list of rooms in SetRoom contains an empty room.");
		}
		this.list = rooms;
	}

	public List<Room> getList() {
		return list;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * return the latest used Chat
	 * 
	 * @return
	 */
	public Room latest() {
		if (list.isEmpty()) {
			return null;
		}
		Room res = null;
		for (Room r : list) {
			if (r == null) {
				continue;
			}
			if (r.getChat() == null) {
				continue;
			}
			if (res == null) {
				res = r;
				continue;
			}
			if (r.getChat().getLatestMessage().getDate().isAfter(res.getChat().getLatestMessage().getDate())) {
				res = r;
			}
		}
		return res;
	}

	@Override
	public Iterator<Room> iterator() {
		return list.iterator();
	}

	/**
	 * Return the list of chat of all the rooms
	 * 
	 * @return
	 */
	public SetChats getAllChats() {
		List<Chat> res = new ArrayList<Chat>();
		for (Room r : list) {
			res.add(r.getChat());
		}
		return new SetChats(res);
	}

}
