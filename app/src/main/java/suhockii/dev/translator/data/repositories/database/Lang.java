package suhockii.dev.translator.data.repositories.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

@Entity(nameInDb = "lang",
        indexes = {
                @Index(value = "code, rating DESC", unique = true)
        })
public final class Lang {

    @Id
    private Long id;
    @NotNull
    private String code;
    private String description;
    private int rating;
    @Generated(hash = 380392066)
    public Lang(Long id, @NotNull String code, String description, int rating) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.rating = rating;
    }
    @Generated(hash = 1197397665)
    public Lang() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lang lang = (Lang) o;

        if (rating != lang.rating) return false;
        if (id != null ? !id.equals(lang.id) : lang.id != null) return false;
        if (code != null ? !code.equals(lang.code) : lang.code != null) return false;
        return description != null ? description.equals(lang.description) : lang.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + rating;
        return result;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}