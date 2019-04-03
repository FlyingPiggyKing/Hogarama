package com.gepardec.hogarama.domain.watering;

import java.util.List;

public interface WateringConfigDAO {

	void save(WateringConfigData wconf);

    void delete(WateringConfigData wconf);

    void update(WateringConfigData wconf);

    List<WateringConfigData> getAll();

	WateringConfigData getBySensorName(String id);

}
