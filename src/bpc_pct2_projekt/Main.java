package bpc_pct2_projekt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import Database_sql.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        database.loadFromDatabase();
        

        boolean run = true;

        while (run) {
            System.out.println("\n======= STUDENTSKÁ DATABÁZE =======");
            System.out.println("1. Přidat nového studenta");
            System.out.println("2. Přidat známku studentovi");
            System.out.println("3. Propustit studenta");
            System.out.println("4. Najít studenta podle ID");
            System.out.println("5. Spustit dovednost studenta");
            System.out.println("6. Abecedně seřazený výpis studentů");
            System.out.println("7. Výpis studijního průměru podle oborů");
            System.out.println("8. Počet studentů v jednotlivých skupinách");
            System.out.println("9. Uložit studenta do souboru");
            System.out.println("10. Načíst studenta ze souboru");
            System.out.println("11. Ukončit program");
            System.out.print("Zadej volbu: ");

            int volba;
            
            String input = scanner.nextLine();

            try {
                volba = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Chyba: Neplatná volba. Zadej číslo.");
                continue; 
            }


            switch (volba) {
                case 1:
                	 System.out.println("\n--- Přidání nového studenta ---");
                	    System.out.print("Zadejte jméno studenta: ");
                	    String firstName = "";
                	    
                	  
                	    try {
                	        firstName = scanner.nextLine().trim();
                	        if (firstName.isEmpty()) {
                	            System.out.println("Jméno nesmí být prázdné.");
                	            break;
                	        }
                	    } catch (Exception e) {
                	        System.out.println("Chyba: Neplatný vstup pro jméno.");
                	        break;
                	    }
                	    
                	    
                	    System.out.print("Zadejte příjmení studenta: ");
                	    String lastName = scanner.nextLine().trim();
                	    if (lastName.isEmpty()) {
                	        System.out.println("Příjmení nesmí být prázdné.");
                	        break;
                	    }
                	    
                	    

                	    System.out.print("Zadejte datum narození (ve formátu dd.MM.yyyy): ");
                	    String birthDateStr = scanner.nextLine();
                	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                	    Date birthDate;
                	    try {
                	        birthDate = dateFormat.parse(birthDateStr);
                	        
                	        
                	        if (birthDate.after(new Date())) {
                	            System.out.println("Datum narození je v budoucnosti. Přidání studenta bylo zrušeno.");
                	            break;
                	        }
                	        
                	    } catch (ParseException e) {
                	        System.out.println("Chybný formát data. Přidání studenta bylo zrušeno.");
                	        break;
                	    }

                	    System.out.println("Vyberte skupinu:");
                	    System.out.println("1. Telekomunikace");
                	    System.out.println("2. Kyberbezpečnost");
                	    System.out.print("Zadej volbu: ");
                	    int groupChoice = scanner.nextInt();
                	    scanner.nextLine(); 

                	    if (groupChoice == 1) {
                	        database.addTelecomStudent(firstName, lastName, birthDate);
                	        System.out.println("Student telekomunikací byl úspěšně přidán.");
                	    } else if (groupChoice == 2) {
                	        database.addCybersecStudent(firstName, lastName, birthDate);
                	        System.out.println("Student kyberbezpečnosti byl úspěšně přidán.");
                	    } else {
                	        System.out.println("Neplatná volba skupiny. Student nebyl přidán.");
                	    }
                   
                    break;
                case 2:
                	
                	System.out.println("\n--- Přidání známky studentovi ---");
                    System.out.print("Zadejte ID studenta: ");
                    
                    int studentId;
                    
                    try {
                        studentId = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        System.out.println("Chyba: Zadejte číselné ID.");
                        scanner.nextLine(); 
                        break;
                    }
                    
                  

                    try {
                        Student student = database.findStudentById(studentId);
                        System.out.print("Zadejte známku (1-5): ");
                        int grade = scanner.nextInt();
                        scanner.nextLine(); 

                        student.addGrade(grade);
                        System.out.println("Známka byla úspěšně přidána studentovi: " + student.getFirstName() + " " + student.getLastName());
                    } catch (IllegalStateException e) {
                        System.out.println("Chyba: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Chybná známka: " + e.getMessage());
                    } catch (InputMismatchException e) {
                    	System.out.println("Zadejte číselnou hodnotu.");
                    	scanner.nextLine(); 
                    	
                    }
                	
                	
                    
                    break;
                case 3:
                	
                	System.out.println("\n--- Propustit studenta ---");
                	System.out.print("Zadejte ID studenta: ");
                	int student_id;
                	
                       
                       try {
                    	   student_id = scanner.nextInt();
                           scanner.nextLine(); 
                       } catch (InputMismatchException e) {
                           System.out.println("Chyba: Zadejte číselné ID.");
                           scanner.nextLine(); 
                           break;
                       }

                	try {
                	    database.removeStudentById(student_id);

                	    System.out.println("Student byl smazán");
                	} catch (IllegalStateException e) {
                	    System.out.println("Chyba: " + e.getMessage());
                	}

                    break;
                case 4:
                	System.out.print("Zadejte ID studenta: ");
                	int stnd_id ;
                	
                	
                	 try {
                		 stnd_id = scanner.nextInt();
                         scanner.nextLine(); 
                     } catch (InputMismatchException e) {
                         System.out.println("Chyba: Zadejte číselné ID.");
                         scanner.nextLine(); 
                         break;
                     }

                	try {
                	    database.printStudentById(stnd_id);

                	} catch (IllegalStateException e) {
                	    System.out.println("Chyba: " + e.getMessage());
                	}
                   
                    break;
                case 5:
                	
                	System.out.println("\n--- Spustit dovednost studenta ---");
                    System.out.print("Zadejte ID studenta: ");
                    int s_id;
                   
                    
                    
               	 try {
               		s_id = scanner.nextInt();
                     scanner.nextLine(); 
                 } catch (InputMismatchException e) {
                     System.out.println("Chyba: Zadejte číselné ID.");
                     scanner.nextLine(); 
                     break;
                 }

                    try {
                        database.executeSkillById(s_id);     
                    } catch (IllegalStateException e) {
                        System.out.println("Chyba: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Chybná známka: " + e.getMessage());
                    }
                	
                   
                    break;
                case 6:
                   database.printAllStudentsSortedByLastName();
                    break;
                case 7:
                    database.AverageGradeByGroup();
                    break;
                case 8:
                    database.GroupCount();
                    break;
                case 9:
                    System.out.println("\n--- Uložení studenta do souboru ---");
                    try {
                        System.out.print("Zadejte ID studenta: ");
                        int saveId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Zadejte název souboru pro uložení (např. student.dat): ");
                        String fileName = scanner.nextLine().trim();

                        if (fileName.isEmpty()) {
                            System.out.println("Chyba: Název souboru nesmí být prázdný.");
                            break;
                        }

                        database.saveStudentToFile(saveId, fileName);
                        System.out.println("Student byl úspěšně uložen.");
                    } catch (NumberFormatException e) {
                        System.out.println("Chyba: Neplatné ID (musí být číslo).");
                    } catch (IllegalStateException | IllegalArgumentException e) {
                        System.out.println("Chyba: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Neočekávaná chyba při ukládání: " + e.getMessage());
                    }
                    break;

                case 10:
                    System.out.println("\n--- Načtení studenta ze souboru ---");
                    try {
                        System.out.print("Zadejte název souboru (např. student.dat): ");
                        String fileToLoad = scanner.nextLine().trim();

                        if (fileToLoad.isEmpty()) {
                            System.out.println("Chyba: Název souboru nesmí být prázdný.");
                            break;
                        }

                        System.out.print("Zadejte očekávané ID studenta: ");
                        int expectedId = Integer.parseInt(scanner.nextLine());

                        database.loadStudentFromFile(fileToLoad, expectedId);
                        System.out.println("Student byl úspěšně načten.");
                    } catch (NumberFormatException e) {
                        System.out.println("Chyba: Neplatné ID (musí být číslo).");
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Chyba: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Neočekávaná chyba při načítání: " + e.getMessage());
                    }
                    break;               
                case 11:
                	
                    database.saveToDatabase();
                    run = false;
                    break;
                default:
                    System.out.println("Neplatná volba. Zkus to znovu.");
            }
        }

        scanner.close();
        System.out.println("Program byl ukončen.");
    }
}
