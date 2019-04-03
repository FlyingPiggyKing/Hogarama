package com.gepardec.hogarama.domain.watering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryWateringConfigDAO implements WateringConfigDAO {

	private HashMap<String, WateringConfigData> store = new HashMap<>();

	@Override
	public void save(WateringConfigData wconf) {
		store.put(wconf.getSensorName(), wconf);
	}

	@Override
	public void delete(WateringConfigData wconf) {
		store.remove(wconf.getSensorName());
	}

	@Override
	public void update(WateringConfigData wconf) {
		store.replace(wconf.getSensorName(), wconf);
	}

	@Override
	public List<WateringConfigData> getAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public WateringConfigData getBySensorName(String id) {
		return store.get(id);
	}

	void setUpForTest() {
		// TODO Auto-generated method stub

	}

}
