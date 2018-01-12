import org.w3c.dom.*;
//Les 5 import suivants si on veut exporter en XML...
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Test
{
   public static void main(String[] args) throws Exception
   {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document document = db.newDocument();

//crée un Node racine
      Node racine = document.createElement("Racine");
//et l'ajoute au document...
      document.appendChild(racine);

//Maintenant on ajout noeud1 à la racine
      Node noeud1 = ajoutNoeud(racine,"aaa", "1er noeud");
//et un fils à noeud1
      ajoutNoeud(noeud1,"bbb", "Enfant du 1er noeud");

//Un 2e noeud à la racine
      Node noeud2 = ajoutNoeud(racine,"ccc", "2e noeud");
//Avec 2 fils...
      ajoutNoeud(noeud2,"ddd", "Ainé du 2e noeud");
      ajoutNoeud(noeud2,"eee", "Cadet du 2e noeud");
      ajoutNoeud(noeud2,"fff", "Dernier enfant du 2e noeud");

//Pour transformer en XML, ça permet de voir si le document est bien construit
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer t = tf.newTransformer();
      t.transform(new DOMSource(document),new StreamResult(new File("toto.xml")));
//

//Pour parcourir le document et l'afficher
      System.out.println(affichage(document.getFirstChild(), 0));
   }

   public static Node ajoutNoeud(Node parent, String valeur1, String valeur2)
   {
      Element element = parent.getOwnerDocument().createElement("Noeud");
      element.setAttribute("code",valeur1);
      element.setAttribute("texte",valeur2);
      parent.appendChild(element);
	  
	  //log
	  System.out.println(element);
	  
      return element;
   }
   
   /**
    * Parcours par récurrence le noeud N, pour afficher ou faire tout autre
    * traitement sur l'arborescence
    * @param Node N le noeud à afficher
    * @param int profondeur pour connaître la profondeur où on est
    */
   public static String affichage(Node N, int profondeur) {
      String ret = "";
      Node fils;
      if (N != null) {
         if (N.hasChildNodes()) {
            //Premier enfant
            fils = N.getFirstChild();
            while (fils != null) {
               //ici le traitement spécifique
               ret += "\n";
               for (int i = 0; i <= profondeur; i++)
                  ret += "\t";
               //Pour récupérer la valeur d'un attribut :
               ret += ((Element)fils).getAttribute("code")
                    + " " + ((Element)fils).getAttribute("texte");
               
               //récurrence : affiche les enfants de fils.
               ret += affichage(fils, profondeur + 1);
               //Passe au frère suivant
               fils = fils.getNextSibling();
            }
         }
      }
      return ret;
   }
}