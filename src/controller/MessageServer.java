package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.mysql.jdbc.Messages;

import model.Message;

public class MessageServer implements Iterable<Message> {

	private Map<Integer, List<Message>> messages;
	private List<Message> selected;
	
	public MessageServer() {
		messages = new TreeMap<Integer, List<Message>>();
		selected = new ArrayList<Message>();
		
		List<Message> list = new ArrayList<Message>();
		list.add(new Message("Have you seen my uncle Bob yet?", "Is my uncle still in the pub?"));
		list.add(new Message("Have you listened to the new song of The Doors?", "Aren't they disbanded like 45years ago?"));
		messages.put(0, list);
		
		list = new ArrayList<Message>();
		list.add(new Message("Which Feri?", "The one who has never seen a fanny"));
		messages.put(1, list);
		
		list = new ArrayList<Message>();
		list.add(new Message("Did you see the new something in the something?", "Did you do something in the sea?"));
		list.add(new Message("Where is my nailcutter?", "Did you steal my parachute?"));
		messages.put(2, list);
		
	}
	
	public void setSelectedServers(Set<Integer> servers) {
		selected.clear();
		for(int id: servers) {
			if (messages.containsKey(id)) {
				selected.addAll(messages.get(id));
			}
		}
	}
	
	public int messageCount() {
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		
		return new MessageIterator(selected);
	}
}
class MessageIterator implements Iterator {
	
	private Iterator<Message> iterator;

	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}

	@Override
	public void remove() {
		iterator.remove();
		}

	@Override
	public boolean hasNext() {
		
		return iterator.hasNext();
	}

	@Override
	public Object next() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iterator.next();
	}
	
}
