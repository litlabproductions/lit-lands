package com.litlabproductions.litlands.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.litlabproductions.litlands.LitLands;
import com.litlabproductions.litlands.Screens.MenuScreen;
import com.litlabproductions.litlands.Screens.PlayScreen;
import com.litlabproductions.litlands.Sprites.Enemy;
import com.litlabproductions.litlands.Sprites.InteractiveTileObject;
import com.litlabproductions.litlands.Sprites.Player;

/**
 * Created by David on 9/24/2017.
 */

public class WorldContactListener implements ContactListener
{
    private Player player;
    private PlayScreen playScreen;

        // ~ Called when two obj makes collision.
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

            // ~ 2 fix, we dont know which one it is, so we test head first,
            //   if it is get head and obj that it collides with
            //   2 fixtures represendint them. Check if obj uiser data is null
            //   next check if its an interactive tile onj. then pass to on head hit.
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head")
        {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() instanceof InteractiveTileObject)
            {
                ((InteractiveTileObject) object.getUserData()).onHeadHit(((Player) fixA.getUserData()));
            }
        }
        switch (cDef)
        {
            case LitLands.PLAYER_HEAD_BIT | LitLands.BRICK_BIT:


            case LitLands.PLAYER_BIT | LitLands.PORTAL_BIT:


                if(fixA.getFilterData().categoryBits == LitLands.PLAYER_BIT)
                    ((Player)fixA.getUserData()).screen.getGame().setScreen(new MenuScreen(((Player)fixA.getUserData()).screen.getGame()));

                else        // ~ Must be this one then according to the condition/
                    ((Player)fixB.getUserData()).screen.getGame().setScreen(new MenuScreen(((Player)fixB.getUserData()).screen.getGame()));
                break;


            case LitLands.PLAYER_HEAD_BIT | LitLands.COIN_BIT:
            if(fixA.getFilterData().categoryBits == LitLands.PLAYER_HEAD_BIT)

                ((InteractiveTileObject) fixB.getUserData()).onHeadHit(((Player) fixA.getUserData()));
            else                // ~ If fixture A / B are the tile objects, then we need to pass the other, visa versa.
                ((InteractiveTileObject) fixA.getUserData()).onHeadHit(((Player) fixB.getUserData()));
            break;

                // ~ Reset double jump counter.
            case LitLands.PLAYER_BIT | LitLands.GROUND_BIT:
                    //screen.getPlayer().setJumpCount(0);
                break;

            case LitLands.ENEMY_HEAD_BIT | LitLands.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == LitLands.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead(((Player) fixB.getUserData()));
                else
                    ((Enemy)fixB.getUserData()).hitOnHead(((Player) fixA.getUserData()));
                break;
            case LitLands.ENEMY_BIT | LitLands.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == LitLands.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case LitLands.PLAYER_BIT | LitLands.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == LitLands.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else                // ~ If fixture A / B are the tile objects, then we need to pass the other, visa versa.
                    ((Player) fixB.getUserData()).hit((Enemy)fixA.getUserData());

                break;
            case LitLands.ENEMY_BIT | LitLands.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).onEnemyHit((Enemy)fixB.getUserData());
                    ((Enemy)fixB.getUserData()).onEnemyHit((Enemy)fixA.getUserData());
                break;

            case LitLands.ITEM_BIT | LitLands.OBJECT_BIT:
               // if(fixA.getFilterData().categoryBits == LitLands.ITEM_BIT)
                   // ((Item)fixA.getUserData()).reverseVelocity(true, false);
                //else        // ~ Must be this one then according to the condition/
                //    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case LitLands.ITEM_BIT | LitLands.PLAYER_BIT:
               // if(fixA.getFilterData().categoryBits == LitLands.ITEM_BIT)
                 //   ((Item)fixA.getUserData()).use((Player) fixB.getUserData());
             //   else        // ~ Must be this one then according to the condition/
                //    ((Item)fixB.getUserData()).use((Player) fixA.getUserData());
                break;
        }

    }

        // ~ Post collision.
    @Override
    public void endContact(Contact contact){
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {
    }
}
