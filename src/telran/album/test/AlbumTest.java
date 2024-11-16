package telran.album.test;

import telran.album.model.Photo;
import telran.album.model.dao.Album;
import telran.album.model.dao.AlbumImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    private Album album;
    private Photo[] photos;
    private final int capacity = 6;

    private final Comparator<Photo> comparator = (p1, p2) -> {
        int res = Integer.compare(p1.getAlbumId(), p2.getAlbumId());

        return res != 0 ? res : Integer.compare(p1.getPhotoId(), p2.getPhotoId());
    };

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        album = new AlbumImpl(capacity);
        photos = new Photo[capacity];

       photos[0]=new Photo(120,100,"Forest", "htttp1", LocalDateTime.of(2012,4,5,13, 45));
       photos[1]=new Photo(120,101,"Lake", "htttp2", LocalDateTime.of(2012, 6 , 17, 15, 8));
       photos[2]=new Photo(120,102,"Camp", "htttp3", LocalDateTime.of(2024, 10, 7, 13, 6));
       photos[3]=new Photo(120,103,"Sea", "htttp4", LocalDateTime.of(2023, 12, 13, 9, 45));
       photos[4]=new Photo(120,104,"Mountain", "htttp5", LocalDateTime.of(2022, 6, 18, 10,10));

        for(int i=0; i<photos.length; i++) {
            album.addPhoto(photos[i]);
        }
    };

    @org.junit.jupiter.api.Test
    void testAddPhoto() {
        assertFalse(album.addPhoto(null));
        assertFalse(album.addPhoto(photos[2]));
        Photo photo = new Photo(120, 105, "Jackson", "http6", LocalDateTime.now());
        assertTrue(album.addPhoto(photo));
        assertEquals(6, album.size());
        photo = new Photo(120,104,"Mountain", "htttp5", LocalDateTime.of(2022, 6, 18, 10,10));
        assertFalse(album.addPhoto(photo));

    }

    @org.junit.jupiter.api.Test
    void testRemovePhoto() {

        assertTrue(album.removePhoto(100,120));
        assertEquals(4, album.size());
        assertFalse(album.removePhoto(100,120));
        assertFalse(album.removePhoto(600,120));
    }

    @org.junit.jupiter.api.Test
    void testUpdatePhoto() {
        assertTrue(album.updatePhoto(100,120, "URL"));
        assertEquals(photos[0].getUrl(), "URL");
        assertFalse(album.updatePhoto(700,120, "URL"));
        assertFalse(album.updatePhoto(100,900, "URL"));

    }

    @org.junit.jupiter.api.Test
    void testGetPhotoFromAlbum() {
        assertEquals(photos[4], album.getPhotoFromAlbum(120,104));
        assertNotEquals(photos[2], album.getPhotoFromAlbum(120,104));
        assertEquals(photos[0], album.getPhotoFromAlbum(120,100));
    }

    @org.junit.jupiter.api.Test
    void testGetAllPhotoFromAlbum() {

        Photo [] expected = {
        new Photo(120,100,"Forest", "htttp1", LocalDateTime.of(2012,4,5,13, 45)),
        new Photo(120,101,"Lake", "htttp2", LocalDateTime.of(2012, 6 , 17, 15, 8)),
        new Photo(120,102,"Camp", "htttp3", LocalDateTime.of(2024, 10, 7, 13, 6)),
        new Photo(120,103,"Sea", "htttp4", LocalDateTime.of(2023, 12, 13, 9, 45)),
        new Photo(120,104,"Mountain", "htttp5", LocalDateTime.of(2022, 6, 18, 10,10)),
        };

        Photo [] actual= album.getAllPhotoFromAlbum(120);
//        System.out.println(Arrays.toString(actual));
        Arrays.sort(actual, comparator);
        Arrays.equals(actual, expected);

        expected = new Photo [] {};
        actual= album.getAllPhotoFromAlbum(156);
        Arrays.equals(actual, expected);
    }

    @org.junit.jupiter.api.Test
    void testGetPhotoBetweenDate() {

//        assertNull(album.getPhotoBetweenDate(LocalDate.of(1990,9,13), LocalDate.of(1994,7,19)));
        Photo [] actual = {
                new Photo(120,102,"Camp", "htttp3", LocalDateTime.of(2024, 10, 7, 13, 6)),
                new Photo(120,103,"Sea", "htttp4", LocalDateTime.of(2023, 12, 13, 9, 45)),
                new Photo(120,104,"Mountain", "htttp5", LocalDateTime.of(2022, 6, 18, 10,10)),
        };

        Photo [] expected= album.getPhotoBetweenDate(LocalDate.of(2022,1,1), LocalDate.of(2025,1,1));
        Arrays.sort(expected,comparator);
        Arrays.equals(actual, expected);

        expected = new Photo [] {};
        actual=album.getPhotoBetweenDate(LocalDate.of(1990,9,13), LocalDate.of(1994,7,19));
    }

    @org.junit.jupiter.api.Test
    void testSize() {
        assertEquals(5, album.size());
    }
}