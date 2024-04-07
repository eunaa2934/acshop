package com.naeun2934.acshop;

import com.naeun2934.acshop.common.Address;
import com.naeun2934.acshop.product.Product;
import com.naeun2934.acshop.product.ProductCategory;
import com.naeun2934.acshop.product.ProductImage;
import com.naeun2934.acshop.product.ProductOption;
import com.naeun2934.acshop.seller.Seller;
import com.naeun2934.acshop.user.User;
import com.naeun2934.acshop.user.UserProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class MakeData {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("acshop");
        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();

        try {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            User user = new User("test@test.test", "テスト", bCryptPasswordEncoder.encode("test"), null,
                    UserProvider.HOMEPAGE,
                    null, null);
            em.persist(user);

            Seller seller = new Seller("naeun2934@gmail.com", "naeun", "aaa",
                    new Address("0000000", "sellerAddress1", "sellerAddress2"));

            Product earingsStud1 = new Product("EaringsStud1", "",
                    "Tiffany round diamonds catch the light and make it dance. Earrings in platinum with round brilliant diamonds. Carat total weight .16. Original designs copyrighted by the Nando and Elsa Peretti Foundation.\n\n - Carat total weight .16\n - Product number:60017555");
            Thread.sleep(1000);

            Product earingsHoop1 = new Product("EaringsHoop1", "",
                    "Inspired by the power of togetherness and inclusivity, Tiffany Lock is a bold and visual statement about the personal bonds that make us who we are. Designed to be worn by all genders, Tiffany Lock designs are inspired by the functionality of a padlock, an important motif from The Tiffany Archives. These earrings feature a spring closure for an uninterrupted look. They’re expertly crafted in 18k with hand-set diamonds.\n\n - 18k with round brilliant diamonds\n - Carat total weight .19\n - Motif size, medium\n - Product number:72342771");
            Thread.sleep(1000);

            Product earingsDropAndDangle1 = new Product("EaringsDropAndDangle1", "",
                    "Inspired by the power of togetherness and inclusivity, Tiffany Lock is a bold and visual statement about the personal bonds that make us who we are. Designed to be worn by all genders, Tiffany Lock designs are inspired by the functionality of a padlock, an important motif from The Tiffany Archives. These earrings feature a spring closure for an uninterrupted look. They’re expertly crafted in 18k yellow gold with hand-set diamonds.\n\n - 18k with round brilliant diamonds\n - Carat total weight .19\n - Motif size, medium\n - Product number:72342771");
            Thread.sleep(1000);

            Product braceletsBangle1 = new Product("BraceletsBangle1", "",
                    "Inspired by the power of togetherness and inclusivity, Tiffany Lock is a bold and visual statement about the personal bonds that make us who we are. Designed to be worn by all genders, the Tiffany Lock bangle features an innovative clasp that echoes the functionality of a padlock, an important motif from The Tiffany Archives. This style is expertly crafted in 18k.\n\n - 18kSize medium\n - Fits wrists up to 6.25”\n - For your recommended Tiffany Lock bracelet size, please view our size guide below.\n - Hidden closure");

            seller.addProduct(earingsStud1);
            seller.addProduct(earingsHoop1);
            seller.addProduct(earingsDropAndDangle1);
            seller.addProduct(braceletsBangle1);

            ProductCategory productCategoryEarings = new ProductCategory("Earings", "", null);
            productCategoryEarings.addProduct(earingsStud1);
            productCategoryEarings.addProduct(earingsHoop1);
            productCategoryEarings.addProduct(earingsDropAndDangle1);

            ProductCategory productCategoryStud = new ProductCategory("Stud",
                    "From delicate diamond studs to statement rose gold designs, our style-defining earrings are striking worn solo or as part of an earring stack.",
                    productCategoryEarings);
            productCategoryStud.addProduct(earingsStud1);

            ProductCategory productCategoryHoop = new ProductCategory("Hoop",
                    "A jewelry box essential, hoops work equally well for day or night. Explore expertly crafted diamond hoop earrings, gold hoop earrings, sterling silver hoop earrings and more.",
                    productCategoryEarings);
            productCategoryHoop.addProduct(earingsHoop1);

            ProductCategory productCategoryDropAndDangle = new ProductCategory("Drop and Dangle",
                    "From classic styles to bold takes, our collection of drop earrings turns heads every time.",
                    productCategoryEarings);
            productCategoryDropAndDangle.addProduct(earingsDropAndDangle1);

            ProductCategory productCategoryBracelets = new ProductCategory("Bracelets", "", null);
            productCategoryBracelets.addProduct(braceletsBangle1);

            ProductCategory productCategoryBangle = new ProductCategory("Bangle",
                    "Our collection of sterling silver and 18k yellow, white and rose gold bangles feature eye-catching details and sparkling diamonds—a must-have to transform any day or night ensemble.",
                    productCategoryBracelets);
            productCategoryBangle.addProduct(braceletsBangle1);

            ProductOption productOptionStud1Silver = new ProductOption(0, "Silver", new BigDecimal(1000), 10);
            earingsStud1.addProductOption(productOptionStud1Silver);

            ProductOption productOptionStud1Gold = new ProductOption(1, "Gold", new BigDecimal(2000), 20);
            earingsStud1.addProductOption(productOptionStud1Gold);

            ProductOption productOptionStud1RoseGold = new ProductOption(2, "RoseGold",
                    new BigDecimal(3000),
                    30);
            earingsStud1.addProductOption(productOptionStud1RoseGold);

            ProductOption productOptionHoop1Silver = new ProductOption(0, "Silver", new BigDecimal(1000),
                    10);
            earingsHoop1.addProductOption(productOptionHoop1Silver);

            ProductOption productOptionHoop1Gold = new ProductOption(1, "Gold", new BigDecimal(2000),
                    20);
            earingsHoop1.addProductOption(productOptionHoop1Gold);

            ProductOption productOptionHoop1RoseGold = new ProductOption(2, "RoseGold",
                    new BigDecimal(3000),
                    30);
            earingsHoop1.addProductOption(productOptionHoop1RoseGold);

            ProductOption productOptionDropAndDangle1Silver = new ProductOption(0, "Silver",
                    new BigDecimal(1000), 10);
            earingsDropAndDangle1.addProductOption(productOptionDropAndDangle1Silver);

            ProductOption productOptionDropAndDangle1Gold = new ProductOption(1, "Gold",
                    new BigDecimal(2000), 20);
            earingsDropAndDangle1.addProductOption(productOptionDropAndDangle1Gold);

            ProductOption productOptionDropAndDangle1RoseGold = new ProductOption(2, "RoseGold",
                    new BigDecimal(3000), 30);
            earingsDropAndDangle1.addProductOption(productOptionDropAndDangle1RoseGold);

            ProductOption productOptionBangle1Silver = new ProductOption(0, "Silver",
                    new BigDecimal(1000), 10);
            braceletsBangle1.addProductOption(productOptionBangle1Silver);

            ProductOption productOptionBangle1Gold = new ProductOption(1, "Gold",
                    new BigDecimal(2000),
                    20);
            braceletsBangle1.addProductOption(productOptionBangle1Gold);

            ProductOption productOptionBangle1RoseGold = new ProductOption(2, "RoseGold",
                    new BigDecimal(3000), 30);
            braceletsBangle1.addProductOption(productOptionBangle1RoseGold);

            // https://www.tiffany.com/jewelry/earrings/elsa-peretti-diamonds-by-the-yard-earrings-GRP01656/
            ProductImage productImageStud1Silver1 = new ProductImage(
                    "orinigalEaringsStud1Silver1.png", "storedEaringsStud1Silver1.png");
            productOptionStud1Silver.addProductImage(productImageStud1Silver1);

            ProductImage productImageStud1Silver2 = new ProductImage(
                    "orinigalEaringsStud1Silver2.png", "storedEaringsStud1Silver2.png");
            productOptionStud1Silver.addProductImage(productImageStud1Silver2);

            ProductImage productImageStud1Silver3 = new ProductImage("orinigalEaringsStud1Silver3.png",
                    "storedEaringsStud1Silver3.png");
            productOptionStud1Silver.addProductImage(productImageStud1Silver3);

            // https://www.tiffany.com/jewelry/earrings/elsa-peretti-diamonds-by-the-yard-earrings-GRP00118/
            ProductImage productImageStud1Gold1 = new ProductImage("orinigalEaringsStud1Gold1.png",
                    "storedEaringsStud1Gold1.png");
            productOptionStud1Gold.addProductImage(productImageStud1Gold1);

            ProductImage productImageStud1Gold2 = new ProductImage("orinigalEaringsStud1Gold2.png",
                    "storedEaringsStud1Gold2.png");
            productOptionStud1Gold.addProductImage(productImageStud1Gold2);

            ProductImage productImageStud1Gold3 = new ProductImage("orinigalEaringsStud1Gold3.png",
                    "storedEaringsStud1Gold3.png");
            productOptionStud1Gold.addProductImage(productImageStud1Gold3);

            // https://www.tiffany.com/jewelry/earrings/elsa-peretti-diamonds-by-the-yard-earrings-GRP05171/
            ProductImage productImageStud1RoseGold1 = new ProductImage(
                    "orinigalEaringsStud1RoseGold1.png", "storedEaringsStud1RoseGold1.png");
            productOptionStud1RoseGold.addProductImage(productImageStud1RoseGold1);

            ProductImage productImageStud1RoseGold2 = new ProductImage("orinigalEaringsStud1RoseGold2.png",
                    "storedEaringsStud1RoseGold2.png");
            productOptionStud1RoseGold.addProductImage(productImageStud1RoseGold2);

            ProductImage productImageStud1RoseGold3 = new ProductImage("orinigalEaringsStud1RoseGold3.png",
                    "storedEaringsStud1RoseGold3.png");
            productOptionStud1RoseGold.addProductImage(productImageStud1RoseGold3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-lock-earrings-72342887/
            ProductImage productImageHoop1Silver1 = new ProductImage("orinigalEaringsHoop1Silver1.png",
                    "storedEaringsHoop1Silver1.png");
            productOptionHoop1Silver.addProductImage(productImageHoop1Silver1);

            ProductImage productImageHoop1Silver2 = new ProductImage(
                    "orinigalEaringsHoop1Silver2.png", "storedEaringsHoop1Silver2.png");
            productOptionHoop1Silver.addProductImage(productImageHoop1Silver2);

            ProductImage productImageHoop1Silver3 = new ProductImage("orinigalEaringsHoop1Silver3.png",
                    "storedEaringsHoop1Silver3.png");
            productOptionHoop1Silver.addProductImage(productImageHoop1Silver3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-lock-earrings-72342429/
            ProductImage productImageHoop1Gold1 = new ProductImage("orinigalEaringsHoop1Gold1.png",
                    "storedEaringsHoop1Gold1.png");
            productOptionHoop1Gold.addProductImage(productImageHoop1Gold1);

            ProductImage productImageHoop1Gold2 = new ProductImage("orinigalEaringsHoop1Gold2.png",
                    "storedEaringsHoop1Gold2.png");
            productOptionHoop1Gold.addProductImage(productImageHoop1Gold2);

            ProductImage productImageHoop1Gold3 = new ProductImage("orinigalEaringsHoop1Gold3.png",
                    "storedEaringsHoop1Gold3.png");
            productOptionHoop1Gold.addProductImage(productImageHoop1Gold3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-lock-earrings-72342429/
            ProductImage productImageHoop1RoseGold1 = new ProductImage("orinigalEaringsHoop1RoseGold1.png",
                    "storedEaringsHoop1RoseGold1.png");
            productOptionHoop1RoseGold.addProductImage(productImageHoop1RoseGold1);

            ProductImage productImageHoop1RoseGold2 = new ProductImage("orinigalEaringsHoop1RoseGold2.png",
                    "storedEaringsHoop1RoseGold2.png");
            productOptionHoop1RoseGold.addProductImage(productImageHoop1RoseGold2);

            ProductImage productImageHoop1RoseGold3 = new ProductImage("orinigalEaringsHoop1RoseGold3.png",
                    "storedEaringsHoop1RoseGold3.png");
            productOptionHoop1RoseGold.addProductImage(productImageHoop1RoseGold3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-t-bar-earrings-66964116/
            ProductImage productImageDropAndDangle1Silver1 = new ProductImage("orinigalEaringsDropAndDangle1Silver1.png",
                    "storedEaringsDropAndDangle1Silver1.png");
            productOptionDropAndDangle1Silver.addProductImage(productImageDropAndDangle1Silver1);

            ProductImage productImageDropAndDangle1Silver2 = new ProductImage("orinigalEaringsDropAndDangle1Silver2.png",
                    "storedEaringsDropAndDangle1Silver2.png");
            productOptionDropAndDangle1Silver.addProductImage(productImageDropAndDangle1Silver2);

            ProductImage productImageDropAndDangle1Silver3 = new ProductImage("orinigalEaringsDropAndDangle1Silver3.png",
                    "storedEaringsDropAndDangle1Silver3.png");
            productOptionDropAndDangle1Silver.addProductImage(productImageDropAndDangle1Silver3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-t-bar-earrings-62271868/
            ProductImage productImageDropAndDangle1Gold1 = new ProductImage("orinigalEaringsDropAndDangle1Gold1.png",
                    "storedEaringsDropAndDangle1Gold1.png");
            productOptionDropAndDangle1Gold.addProductImage(productImageDropAndDangle1Gold1);

            ProductImage productImageDropAndDangle1Gold2 = new ProductImage("orinigalEaringsDropAndDangle1Gold2.png",
                    "storedEaringsDropAndDangle1Gold2.png");
            productOptionDropAndDangle1Gold.addProductImage(productImageDropAndDangle1Gold2);

            ProductImage productImageDropAndDangle1Gold3 = new ProductImage("orinigalEaringsDropAndDangle1Gold3.png",
                    "storedEaringsDropAndDangle1Gold3.png");
            productOptionDropAndDangle1Gold.addProductImage(productImageDropAndDangle1Gold3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-t-bar-earrings-62271884/
            ProductImage productImageDropAndDangle1RoseGold1 = new ProductImage("orinigalEaringsDropAndDangle1RoseGold1.png",
                    "storedEaringsDropAndDangle1RoseGold1.png");
            productOptionDropAndDangle1RoseGold.addProductImage(productImageDropAndDangle1RoseGold1);

            ProductImage productImageDropAndDangle1RoseGold2 = new ProductImage("orinigalEaringsDropAndDangle1RoseGold2.png",
                    "storedEaringsDropAndDangle1RoseGold2.png");
            productOptionDropAndDangle1RoseGold.addProductImage(productImageDropAndDangle1RoseGold2);

            ProductImage productImageDropAndDangle1RoseGold3 = new ProductImage("orinigalEaringsDropAndDangle1RoseGold3.png",
                    "storedEaringsDropAndDangle1RoseGold3.png");
            productOptionDropAndDangle1RoseGold.addProductImage(productImageDropAndDangle1RoseGold3);

            // https://www.tiffany.com/jewelry/bracelets/tiffany-lock-bangle-GRP12241/
            ProductImage productImageBangle1Silver1 = new ProductImage("orinigalBraceletsBangle1Silver1.png",
                    "storedBraceletsBangle1Silver1.png");
            productOptionBangle1Silver.addProductImage(productImageBangle1Silver1);

            ProductImage productImageBangle1Silver2 = new ProductImage("orinigalBraceletsBangle1Silver2.png",
                    "storedBraceletsBangle1Silver2.png");
            productOptionBangle1Silver.addProductImage(productImageBangle1Silver2);

            ProductImage productImageBangle1Silver3 = new ProductImage("orinigalBraceletsBangle1Silver3.png",
                    "storedBraceletsBangle1Silver3.png");
            productOptionBangle1Silver.addProductImage(productImageBangle1Silver3);

            // https://www.tiffany.com/jewelry/bracelets/tiffany-lock-bangle-GRP12223/
            ProductImage productImageBangle1Gold1 = new ProductImage(
                    "orinigalBraceletsBangle1Gold1.png", "storedBraceletsBangle1Gold1.png");
            productOptionBangle1Gold.addProductImage(productImageBangle1Gold1);

            ProductImage productImageBangle1Gold2 = new ProductImage("orinigalBraceletsBangle1Gold2.png",
                    "storedBraceletsBangle1Gold2.png");
            productOptionBangle1Gold.addProductImage(productImageBangle1Gold2);

            ProductImage productImageBangle1Gold3 = new ProductImage("orinigalBraceletsBangle1Gold3.png",
                    "storedBraceletsBangle1Gold3.png");
            productOptionBangle1Gold.addProductImage(productImageBangle1Gold3);

            // https://www.tiffany.com/jewelry/earrings/tiffany-t-bar-earrings-62271884/
            ProductImage productImageBangle1RoseGold1 = new ProductImage("orinigalBraceletsBangle1RoseGold1.png",
                    "storedBraceletsBangle1RoseGold1.png");
            productOptionBangle1RoseGold.addProductImage(productImageBangle1RoseGold1);

            ProductImage productImageBangle1RoseGold2 = new ProductImage("orinigalBraceletsBangle1RoseGold2.png",
                    "storedBraceletsBangle1RoseGold2.png");
            productOptionBangle1RoseGold.addProductImage(productImageBangle1RoseGold2);

            ProductImage productImageBangle1RoseGold3 = new ProductImage("orinigalBraceletsBangle1RoseGold3.png",
                    "storedBraceletsBangle1RoseGold3.png");
            productOptionBangle1RoseGold.addProductImage(productImageBangle1RoseGold3);

            em.persist(seller);

            et.commit();

        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
