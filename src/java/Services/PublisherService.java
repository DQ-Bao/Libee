package Services;

import Models.Publisher;
import DataAccesses.PublisherDataAccess;
import java.util.List;

public class PublisherService {
    private PublisherDataAccess dao;
    
    public PublisherService() {
        this.dao = new PublisherDataAccess();
    }
    
    public List<Publisher> getAll() {
        List<Publisher> publist = dao.getAll();
        return publist;
    }
    public Publisher getById(int id) {
        Publisher pub = dao.getById(id);
        return pub;
    }
    public boolean isExistByName(String name) {
        List<Publisher> dblist = dao.getAll();
        for (int i = 0; i < dblist.size(); i++) {
            if (dblist.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean addOne(String name) {
        boolean success = false;
        if (!isExistByName(name)) {
            success = dao.addOne(name);
        }
        return success;
    }
    public boolean updateOne(Publisher pub) {
        return dao.updateOne(pub);
    }
    public boolean deleteOne(int id) {
        return dao.deleteOne(id);
    }
}
