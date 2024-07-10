package com.neo.country_finder.service.impl;

import com.neo.country_finder.model.db.CountryData;
import com.neo.country_finder.repository.CountryDataRepository;
import com.neo.country_finder.service.WikiDataService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
class WikiDataServiceImpl implements WikiDataService {

    private final CountryDataRepository repository;

    @Override
    @Transactional
    public void initData() throws IOException {
        repository.saveAll(getData());
        System.out.println();
    }

    private Collection<CountryData> getData() throws IOException {
        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_country_calling_codes").get();
        final Set<CountryData> data = new HashSet<>();
        for (Element element : doc.select("table").first().select("tr")) {
            try {
                getCodes(element).forEach(it -> data.add(new CountryData(it, getCountryName(element))));
            } catch (NullPointerException | IndexOutOfBoundsException ignored) {}
        }
        return data;
    }

    private String getCountryName(Element element) {
        String country = element.child(0).textNodes().getFirst().getWholeText().replaceAll("\\n", "");
        return country.equals("United States") || country.equals("Canada") ? "United States, Canada" : country;
    }

    private Collection<String> getCodes(Element element) {
        String[] values = element.child(1).child(1).text().replaceAll("[^\\d ]", "").split(" ");
        if (values.length < 2)
            return Collections.singletonList(values[0]);
        Set<String> result = new HashSet<>();
        for (int i = 1; i < values.length; i++)
            result.add(values[0] + values[i]);
        return result;
    }
}
