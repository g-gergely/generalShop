package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoDb.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDb.getInstance();
        SupplierDao supplierDataStore = SupplierDaoDb.getInstance();

        ProductDao productDataStoreMem = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStoreMem = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStoreMem = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier rome = new Supplier(1,"Rome", "A millenium of Roman virtue");
        Supplier arpino = new Supplier(2,"Arpino", "The blood of Lazio");
        Supplier pella = new Supplier(3, "Pella", "World tour from Pella coming to you soon");
        Supplier sparta = new Supplier(4, "Sparta", "A handful of people against the world");
        Supplier athens = new Supplier(5, "Athens", "First-hand democracy");
        Supplier carthage = new Supplier(6, "Carthage", "Hear the mountains rumble");
        Supplier deluun = new Supplier(7, "Delüün Boldog", "The cradle of the Golden Horde");
        Supplier hazajarat = new Supplier(8, "Hazajarat", "Birth of the Arrow");
        Supplier owari = new Supplier(9, "Owari", "The start of something big");
        Supplier mikagawa = new Supplier(10, "Mikagawa", "Union for all");
        Supplier hiyoshi = new Supplier(11, "Hiyoshi-maru", "Birth place of Toyotomi Hideyoshi");

        if (supplierDataStore.getAll().size() == 0) {
            supplierDataStore.add(rome);
            supplierDataStore.add(arpino);
            supplierDataStore.add(pella);
            supplierDataStore.add(sparta);
            supplierDataStore.add(athens);
            supplierDataStore.add(carthage);
            supplierDataStore.add(deluun);
            supplierDataStore.add(hazajarat);
            supplierDataStore.add(owari);
            supplierDataStore.add(mikagawa);
            supplierDataStore.add(hiyoshi);
        }

        supplierDataStoreMem.add(rome);
        supplierDataStoreMem.add(arpino);
        supplierDataStoreMem.add(pella);
        supplierDataStoreMem.add(sparta);
        supplierDataStoreMem.add(athens);
        supplierDataStoreMem.add(carthage);
        supplierDataStoreMem.add(deluun);
        supplierDataStoreMem.add(hazajarat);
        supplierDataStoreMem.add(owari);
        supplierDataStoreMem.add(mikagawa);
        supplierDataStoreMem.add(hiyoshi);

        //setting up a new product category
        ProductCategory roman = new ProductCategory(1, "Roman", "General", "A roman genera always leads from the front.");
        ProductCategory greek = new ProductCategory(2, "Greek", "General", "Philosophy and world domination from the best.");
        ProductCategory carthaginian = new ProductCategory(3, "Carthaginian", "General", "The most surprising technologies from history.");
        ProductCategory mongolian = new ProductCategory(4, "Mongolian", "General", "Horses like the wind, arrows like death.");
        ProductCategory japanese = new ProductCategory(5, "Japanese", "General", "Tenka Fubu.");
        if (productCategoryDataStore.getAll().size() == 0) {
            productCategoryDataStore.add(roman);
            productCategoryDataStore.add(greek);
            productCategoryDataStore.add(carthaginian);
            productCategoryDataStore.add(mongolian);
            productCategoryDataStore.add(japanese);
        }

        productCategoryDataStoreMem.add(roman);
        productCategoryDataStoreMem.add(greek);
        productCategoryDataStoreMem.add(carthaginian);
        productCategoryDataStoreMem.add(mongolian);
        productCategoryDataStoreMem.add(japanese);

        if (productDataStore.getAll().size() == 0) {
            //setting up products and printing it
            productDataStore.add(new Product(1, "Julius Caesar", 4000, "TAL", "One of the greatest general of the world. Known for his victories over the Gallic tribes and as the main reason for the fall of the Roman Republic", roman, rome));
            productDataStore.add(new Product(2, "Gaius Marius", 750, "TAL", "The seven time consul of Rome known for his military reforms and victories over the Numidians, the Cambri and Teutones.", roman, arpino));
            productDataStore.add(new Product(3, "Alexander The Great", 4500, "TAL", "The greatest general of all time, conqueror of Persia, son of Philip II.", greek, pella));
            productDataStore.add(new Product(4, "Leonidas", 900, "TAL", "The famous Spartan general who held the Thermopülai against the persian masses.", greek, sparta));
            productDataStore.add(new Product(5, "Hannibal Barca", 1700, "TAL", "The greatest Carthaginian general who crossed the Alps with his army to surprise the Roman on their own turf.", carthaginian, carthage));
            productDataStore.add(new Product(6, "Hasdrubal Barca", 1200, "TAL", "Younger brother of the famous Hannibal, held the Roman armies for years at the Iberian peninsula.", carthaginian, carthage));
            productDataStore.add(new Product(7, "Genghis Khan", 3500, "TAL", "Unifier and first Khan of the Mongols. His empire was one of the greatest in world history rivaled only by the British.", mongolian, deluun));
            productDataStore.add(new Product(8, "Jebe", 650, "TAL", "Jebe, the Arrow - one of the most prominent generals of the famous Genghis Khan.", mongolian, hazajarat));
            productDataStore.add(new Product(9, "Oda Nobunaga", 1700, "TAL", "The first of the 3 unifiers of Japan, lord of Owari.", japanese, owari));
            productDataStore.add(new Product(10, "Tokugawa Ieyasu", 1750, "TAL", "The third and final unifier of Japan, his shogunate lasted for centuries.", japanese, mikagawa));
            productDataStore.add(new Product(11, "Pompeius Magnus", 2100, "TAL", "One of the most decorated and celebrated Roman general of his time. Defeated the pirates of the Mediterranean Sea, won the 3rd Mithridatic war and even defeated Julius Caesar on one occasion.", roman, rome));
            productDataStore.add(new Product(12, "Lucius Cornelius Sulla", 800, "TAL", "The first dictator or Rome. Took the city by force 2 times, defeated the revolving Roman allies in the Social War and was victorious over Mithridates not in one, but in two wars", roman, rome));
            productDataStore.add(new Product(13, "Miltiades", 1450, "TAL", "The victorious general of Marathon, also known as a famous chariot racer in his youth.", greek, athens));
            productDataStore.add(new Product(14, "Toyotomi Hideyoshi", 1550, "TAL", "The second great unifier of Japan. Rose from Nobunaga's sandal-bearer to the highest ranks of Japan through his military genius and tactical abilites", japanese, hiyoshi));
            productDataStore.add(new Product(15, "Mago Barca", 500, "TAL", "The youngest of the Carthagian lions - the sons of Hamilkar Barca. A famous general who led many troops to victory in Iberia against the Romans", carthaginian, carthage));
            productDataStore.add(new Product(16, "Ogodei Khan", 3700, "TAL", "The third son of Genghis Khan and second Great Khan of the Mongol Empire, under his rule the Mongol Empire reached its farthest extent west and south", mongolian, deluun));
            productDataStore.add(new Product(17, "Publius Cornelius Scipio Africanus", 1100, "TAL", "A Roman general and later consul who is often regarded as one of the greatest military commanders and strategists of all time", roman, rome));
        }

        productDataStoreMem.add(new Product(1, "Julius Caesar", 4000, "TAL", "One of the greatest general of the world. Known for his victories over the Gallic tribes and as the main reason for the fall of the Roman Republic", roman, rome));
        productDataStoreMem.add(new Product(2, "Gaius Marius", 750, "TAL", "The seven time consul of Rome known for his military reforms and victories over the Numidians, the Cambri and Teutones.", roman, arpino));
        productDataStoreMem.add(new Product(3, "Alexander The Great", 4500, "TAL", "The greatest general of all time, conqueror of Persia, son of Philip II.", greek, pella));
        productDataStoreMem.add(new Product(4, "Leonidas", 900, "TAL", "The famous Spartan general who held the Thermopülai against the persian masses.", greek, sparta));
        productDataStoreMem.add(new Product(5, "Hannibal Barca", 1700, "TAL", "The greatest Carthaginian general who crossed the Alps with his army to surprise the Roman on their own turf.", carthaginian, carthage));
        productDataStoreMem.add(new Product(6, "Hasdrubal Barca", 1200, "TAL", "Younger brother of the famous Hannibal, held the Roman armies for years at the Iberian peninsula.", carthaginian, carthage));
        productDataStoreMem.add(new Product(7, "Genghis Khan", 3500, "TAL", "Unifier and first Khan of the Mongols. His empire was one of the greatest in world history rivaled only by the British.", mongolian, deluun));
        productDataStoreMem.add(new Product(8, "Jebe", 650, "TAL", "Jebe, the Arrow - one of the most prominent generals of the famous Genghis Khan.", mongolian, hazajarat));
        productDataStoreMem.add(new Product(9, "Oda Nobunaga", 1700, "TAL", "The first of the 3 unifiers of Japan, lord of Owari.", japanese, owari));
        productDataStoreMem.add(new Product(10, "Tokugawa Ieyasu", 1750, "TAL", "The third and final unifier of Japan, his shogunate lasted for centuries.", japanese, mikagawa));
        productDataStoreMem.add(new Product(11, "Pompeius Magnus", 2100, "TAL", "One of the most decorated and celebrated Roman general of his time. Defeated the pirates of the Mediterranean Sea, won the 3rd Mithridatic war and even defeated Julius Caesar on one occasion.", roman, rome));
        productDataStoreMem.add(new Product(12, "Lucius Cornelius Sulla", 800, "TAL", "The first dictator or Rome. Took the city by force 2 times, defeated the revolving Roman allies in the Social War and was victorious over Mithridates not in one, but in two wars", roman, rome));
        productDataStoreMem.add(new Product(13, "Miltiades", 1450, "TAL", "The victorious general of Marathon, also known as a famous chariot racer in his youth.", greek, athens));
        productDataStoreMem.add(new Product(14, "Toyotomi Hideyoshi", 1550, "TAL", "The second great unifier of Japan. Rose from Nobunaga's sandal-bearer to the highest ranks of Japan through his military genius and tactical abilites", japanese, hiyoshi));
        productDataStoreMem.add(new Product(15, "Mago Barca", 500, "TAL", "The youngest of the Carthagian lions - the sons of Hamilkar Barca. A famous general who led many troops to victory in Iberia against the Romans", carthaginian, carthage));
        productDataStoreMem.add(new Product(16, "Ogodei Khan", 3700, "TAL", "The third son of Genghis Khan and second Great Khan of the Mongol Empire, under his rule the Mongol Empire reached its farthest extent west and south", mongolian, deluun));
        productDataStoreMem.add(new Product(17, "Publius Cornelius Scipio Africanus", 1100, "TAL", "A Roman general and later consul who is often regarded as one of the greatest military commanders and strategists of all time", roman, rome));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}

