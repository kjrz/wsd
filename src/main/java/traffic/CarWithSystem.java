package main.java.traffic;

import main.java.road.Position;
import main.java.road.Road;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author kjrz
 */
public class CarWithSystem extends Agent implements Car {
    private final String id;
    private final Road road;

    private int position;
    private AID leader;
    private int v; 
	private int fps = 10;
	private int period = 1000 / fps;

    public CarWithSystem(String id, Road road) {
        this.position = 0;
        this.id = id;
        this.road = road;
    }
	
    public CarWithSystem(int position, String id, Road road) {
        this.position = position;
        this.id = id;
        this.road = road;
    }

    @Override
    public int look(int range) {
        return road.look(position, range);
    }

    @Override
    public void move(int steps) {
        position = road.move(position, steps);
    }

    @Override
    public String toString() {
        Position p = road.getPosition(position);
        return String.format("%s@(%d, %d)", id, p.x, p.y);
    }

    // TODO: listen to agent
    // TODO: listen to driver
    
	protected void setup()
	{
		addBehaviour(new Behaviour() //samochod stanal w korku, wyslij zapytanie o dolaczenie 
		{ 

			public void action()
			{
				int pos = look(v);
				if (pos != 0)
				{
					position = pos - 1; //ustaw sie za poprzednikiem
					// i wyslij do niego wiadomosc
					ACLMessage ask = new ACLMessage(ACLMessage.REQUEST);
					// TODO jak dodac adresata?
					
				}				

			}
			public boolean done()
			{
				return false;
			}
		});
		
		addBehaviour(new Behaviour() //otrzymano zapytanie o dolaczenie, wyslij adres lidera badz swoj jesli brak lidera
		{
			public void action()
			{
				MessageTemplate	mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage msg = receive(mt);
				if (msg != null)
				{
					ACLMessage msgRe = new ACLMessage(ACLMessage.AGREE);
					msgRe.addReceiver(msg.getSender());
					
					if (leader.equals(null))
					{
					msgRe.setContent(getAID().getName());
					send(msgRe);
					leader = getAID();
					}
					else 
					{
						msgRe.setContent(leader.getName());
						send(msgRe);
					}
					
				} else
					block();				
			}
			
			public boolean done()
			{
				return false;
			}			

		});		
		
		addBehaviour(new Behaviour() //otrzymano zgode na dolaczenie do grupy, rejestracja jako czlonek zespolu
		{
					
			public void action()
			{
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
				ACLMessage msg = receive(mt);
				if (msg != null)
				{
					DFAgentDescription dfd = new DFAgentDescription();
					dfd.setName(getAID());
					ServiceDescription sd = new ServiceDescription();
					sd.setName(msg.getContent());
					sd.setType(msg.getContent());
					dfd.addServices(sd);
					
					try
					{
						DFService.register(myAgent, dfd);
					} catch (FIPAException e)
					{
						e.printStackTrace();
					}
					
				} else
					block();					
			}
			
			public boolean done()
			{
				return false;
			}
		});
	
		addBehaviour(new TickerBehaviour(this, period) 	//skoryguj predkosc, oblicz polozenie
		{
			protected void onTick()
			{
				if (leader.getLocalName().equals(getAID().getLocalName())) //[lider] wyslij predkosc do powiazanych samochodow
				{						
					if (look(v) == 0) move(v);
					else move(look(v)-1);
					
					DFAgentDescription dfd = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType(getAID().getName());
					dfd.addServices(sd);
					
					SearchConstraints ALL = new SearchConstraints();
					ALL.setMaxResults(new Long(-1));
					
					try
					{
						DFAgentDescription[] result = DFService.search(myAgent,
								dfd, ALL);
						AID[] auto = new AID[result.length];
						for (int i = 0; i < result.length; i++)
						{
							// wyslij aktualna predkosc do wszystkich pojazdow
							auto[i] = result[i].getName();
							ACLMessage sendV = new ACLMessage(
									ACLMessage.INFORM);

							sendV.setContent(""+v);			
							sendV.addReceiver(auto[i]);
							send(sendV);

						}

					} catch (FIPAException fe)
					{
						fe.printStackTrace();
					}
					
					if (v < 20) v++;
				}				
			}
		});
		
		addBehaviour(new Behaviour()
		{
			public void action()
			{
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				ACLMessage msg = receive(mt);
				if (msg != null)
				{
					int n = Integer.parseInt(msg.getContent());
					if (look(n) != 0) move(n);
					else move (look(n)-1);
					
				} else
					block();					
			
			}
			
			public boolean done()
			{

				return false;
			}
			
		});	
	}


}

