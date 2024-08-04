using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerKeybinds : MonoBehaviour
{
    [Header("KeyCodes")]
    public KeyCode escape;
    public KeyCode interact;
    public KeyCode crouch;
    public KeyCode sprint;
    public KeyCode hit;

    public PlayerHandler playerHandler;

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(escape))
        {
            gameObject.GetComponent<MainMenuScript>().optionsUI.SetActive(false);
            gameObject.GetComponent<MainMenuScript>().mainMenuUI.SetActive(true);
        }
        if (Input.GetKeyDown(interact))
        {
            playerHandler.Interact();
        }

        if (Input.GetKey(crouch))
        {
            playerHandler.isCrouching = true;
        }
        else
        {
            playerHandler.isCrouching = false;
        }

        if (Input.GetKey(sprint))
        {
            playerHandler.isSprinting = true;
        }
        else
        {
            playerHandler.isSprinting = false;
        }

        if (Input.GetKeyDown(hit))
        {
            playerHandler.Hit();
        }
    }
}
