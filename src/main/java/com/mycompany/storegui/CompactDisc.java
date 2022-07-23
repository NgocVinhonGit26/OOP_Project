package com.mycompany.storegui;

import java.util.List;
import java.util.ArrayList;

public class CompactDisc extends Disc {
	private String artist;
	private List<Track> trackList = new ArrayList<Track>();

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public List<Track> getTrackList() {
		return trackList;
	}

	public void setTrackList(List<Track> trackList) {
		this.trackList = trackList;
	}

	public CompactDisc(int id, String title, String category, String director, int length, float funds, float cost,
			int quantity, String image, String artist, List<Track> trackList) {
		super(id, title, category, director, length, funds, cost, quantity, image);
		this.artist = artist;
		this.trackList = trackList;
	}

	public void addTrack(Track track) {
		if (trackList.contains(track)) {
			System.out.println("Track already exists");
		} else {
			trackList.add(track);
		}
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof CompactDisc) {
			return this.getTitle().compareToIgnoreCase(((CompactDisc) o).getTitle());
		}
		return -9999;
	}
}
